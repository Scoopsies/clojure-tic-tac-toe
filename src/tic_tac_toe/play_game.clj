(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.core :as move]
            [tic-tac-toe.moves.easy]
            [tic-tac-toe.moves.medium]
            [tic-tac-toe.moves.hard]
            [tic-tac-toe.moves.human-move]
            [tic-tac-toe.moves.replay]
            [clojure.string :as str]
            [tic-tac-toe.state-initializer :as state]))

(declare start-game)

(defn- valid-input? [player-input]
  (some #(= player-input %) ["y" "yes" "n" "no"]))

(defn play-again? []
  (println "Would you like to play again? Y/N")
  (let [player-input (str/lower-case (read-line))]
    (if (valid-input? player-input)
      (or (= player-input "yes")
          (= player-input "y"))
      (recur))))

(defmulti get-play-again :ui)

(defmethod get-play-again :tui [state _]
  (printables/print-formatted (printables/get-board-printables (:board state)))
  (println (printables/get-winner-printable state))
  (if (play-again?)
    (start-game {:ui :tui})
    (println "See you next time!")))

(defmethod get-play-again :gui [state selection]
  (cond
    (= selection "3") {:ui :gui
                       :menu? true
                       :end-game? false
                       :printables printables/player-x-printables}
    (= selection "4") {:end-game? true}
    :else state))

(defn assoc-move [state move]
  (let [x-move (state "X")]
    (if x-move
      (assoc state "O" {:move move} :printables (printables/get-board-size-menu state))
      (assoc state "X" {:move move} :printables printables/player-o-printables))))

(defn get-move-fns [state selection]
  (cond
    (= selection "1") (assoc-move state :human)
    (= selection "2") (assoc-move state :easy)
    (= selection "3") (assoc-move state :medium)
    (= selection "4") (assoc-move state :hard)
    :else state))

(defmulti associate-board :ui)

(defmethod associate-board :tui [state selection]
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
      (assoc updated-state :printables (printables/get-move-printables (:board updated-state)) :menu? false))))

(defn update-move-order [{:keys [move-order]} selection]
  (if selection (vec (conj move-order selection)) move-order))

(defn- ->make-move-state [state selection]
  (let [move-order (update-move-order state selection)
        updated-board (board/update-board selection (:board state))
        replay-moves (:replay-moves state)]
    (cond
      (board/game-over? updated-board) (assoc state :board updated-board :move-order move-order :printables (printables/get-game-over-printable {:board updated-board}) :menu? true :game-over? true)
      replay-moves (assoc state :board updated-board :move-order move-order :printables (printables/get-move-printables updated-board) :replay-moves (rest replay-moves))
      :else (assoc state :board updated-board :move-order move-order :printables (printables/get-move-printables updated-board)))))

(defn make-move [state selection]
  (let [updated-state (->make-move-state state selection)]
    (do (cond
          (:replay? state) nil
          (:id updated-state) (data/update-db updated-state)
          :else (data/add-db updated-state))
        (if (:replay? state) updated-state (data/pull-last-db)))))

(defn- x-and-o-not-set? [state]
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
    (x-and-o-not-set? state) (get-move-fns state selection)
    (board-not-set? state) (get-board state selection)
    (:game-over? state) (get-play-again state selection)
    :else (make-move state selection)))

(defmulti loop-game-play :ui)

(defn get-selection [state]
  (if (:board state) (move/pick-move state) (read-line)))

(defn game-over? [{:keys [board]}]
  (and board (board/game-over? board)))

(defmethod loop-game-play :tui [state]
  (printables/print-formatted (:printables state))
  (let [selection (get-selection state) updated-state (get-next-state state selection)]
    (if (game-over? updated-state)
      (get-play-again updated-state nil)
      (recur updated-state))))

(defn start-game [state]
  (let [state (state/->initial-state state)]
    (loop-game-play state)))