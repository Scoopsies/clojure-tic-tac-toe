(ns tic-tac-toe.play-gamec
  (:require [tic-tac-toe.boardc :as board]
            [tic-tac-toe.data.data-ioc :as data]
            [tic-tac-toe.printablesc :as printables]))

(declare start-game)

(defn get-play-again [state selection]
  (cond
    (= selection "1") {:ui (:ui state) :printables printables/player-x-printables :game-over? false}
    (= selection "2") {:end-game? true :printables ["See you next time!"]}
    :else state))

(defn assoc-move [state move]
  (let [x-move (state "X")]
    (if x-move
      (assoc state "O" move :printables (printables/get-board-size-menu state))
      (assoc state "X" move :printables printables/player-o-printables))))

(defn get-move-fns [state selection]
  (cond
    (= selection "1") (assoc-move state :human)
    (= selection "2") (assoc-move state :easy)
    (= selection "3") (assoc-move state :medium)
    (= selection "4") (assoc-move state :hard)
    :else state))

(defmulti associate-board :ui)

(defmethod associate-board :default [state selection]
  (cond
    (= selection "1") (assoc state :board-size :3x3 :board (range 9))
    (= selection "2") (assoc state :board-size :4x4 :board (range 16))
    (= selection "3") (assoc state :board-size :3x3x3 :board (range 27))
    :else state))

(defmethod associate-board :gui [state selection]
  (cond
    (= selection "1") (assoc state :board-size :3x3 :board (range 9))
    (= selection "2") (assoc state :board-size :4x4 :board (range 16))
    :else state))

(defn get-board [state selection]
  (let [updated-state (associate-board state selection)]
    (if (= updated-state state)
      state
      (assoc updated-state :printables (printables/get-move-printables (:board updated-state))))))

(defn update-move-order [{:keys [move-order]} selection]
  (if selection (vec (conj move-order selection)) move-order))

(defn- ->make-move-state [state selection]
  (let [move-order (update-move-order state selection)
        updated-board (board/update-board selection (:board state))
        replay-moves (:replay-moves state)]
    (cond
      (board/game-over? updated-board) (assoc state :board updated-board :move-order move-order :printables (printables/get-game-over-printable {:board updated-board}) :game-over? true)
      replay-moves (assoc state :board updated-board :move-order move-order :printables (printables/get-move-printables updated-board) :replay-moves (rest replay-moves))
      :else (assoc state :board updated-board :move-order move-order :printables (printables/get-move-printables updated-board)))))

(defn make-move [state selection]
  (let [updated-state (->make-move-state state selection)]
    (do (cond
          (:replay? state) nil
          (:id updated-state) (data/update-db updated-state)
          :else (data/add-db updated-state))
        (if (:replay? state) updated-state (data/pull-last-db)))))

(defn- x-or-o-not-set? [state]
  (or (not (state "X")) (not (state "O"))))

(defn- board-not-set? [state]
  (not (:board state)))

(defn handle-continue [state selection]
  (cond
    (= selection "1") (assoc (:last-game state) :ui (:ui state))
    (= selection "2") (assoc (dissoc state :last-game) :printables printables/player-x-printables)
    :else state))

(defn get-next-state [state selection]
  (cond
    (:last-game state) (handle-continue state selection)
    (x-or-o-not-set? state) (get-move-fns state selection)
    (board-not-set? state) (get-board state selection)
    (:game-over? state) (get-play-again state selection)
    :else (make-move state selection)))