(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.core :as move]
            [tic-tac-toe.moves.easy]
            [tic-tac-toe.moves.medium]
            [tic-tac-toe.moves.hard]
            [tic-tac-toe.moves.human-move]
            [clojure.string :as str]))

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

    (= selection "4") {:ui :gui
                       :menu? true
                       :end-game? true
                       :printables ["" "" "" "Thanks For Playing!"]}
    :else state))

(def player-x-printables ["Who will play as X?"
                          "1. Human"
                          "2. Computer Easy"
                          "3. Computer Medium"
                          "4. Computer Hard"])

(def player-o-printables ["Who will play as O?"
                          "1. Human"
                          "2. Computer Easy"
                          "3. Computer Medium"
                          "4. Computer Hard"])

(defn get-move-printables [board]
  (let [printable-board (printables/get-board-printables board)]
    (conj (vec printable-board) "" (str "Please pick a number 1-" (count board) "."))))

(defn get-move-x [state selection]
  (cond
    (= selection "1") (assoc state "X" {:move :human} :printables printables/player-o-printables)
    (= selection "2") (assoc state "X" {:move :easy} :printables printables/player-o-printables)
    (= selection "3") (assoc state "X" {:move :medium} :printables printables/player-o-printables)
    (= selection "4") (assoc state "X" {:move :hard} :printables printables/player-o-printables)
    :else state))

(defn get-move-o [state selection]
  (cond
    (= selection "1") (assoc state "O" {:move :human} :printables (printables/get-board-size-menu state))
    (= selection "2") (assoc state "O" {:move :easy} :printables (printables/get-board-size-menu state))
    (= selection "3") (assoc state "O" {:move :medium} :printables (printables/get-board-size-menu state))
    (= selection "4") (assoc state "O" {:move :hard} :printables (printables/get-board-size-menu state))
    :else state))

(defn- associate-board [state selection]
  (cond
    (= selection "1") (assoc state :board-size :3x3 :board (range 9))
    (= selection "2") (assoc state :board-size :4x4 :board (range 16))
    (= selection "3") (assoc state :board-size :3x3x3 :board (range 27))
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
      (assoc updated-state :printables (get-move-printables (:board updated-state)) :menu? false))))

(defn make-move [state selection]
  (let [board (:board state) updated-board (core/update-board selection board)]
    (if (board/game-over? board)
      (assoc state :board updated-board :printables (printables/get-game-over-printable state) :menu? true :game-over? true)
      (assoc state :board updated-board :printables (get-move-printables updated-board)))))

(defn next-state [state selection]
  (cond
    (not (state "X")) (get-move-x state selection)
    (not (state "O")) (get-move-o state selection)
    (not (:board state)) (get-board state selection)
    (:game-over? state) (get-play-again state selection)
    :else (make-move state selection)))

(defmulti loop-game-play :ui)

(defn get-selection [state]
  (if (:board state) (move/pick-move state) (read-line)))

(defn game-over? [{:keys [board]}]
  (and board (board/game-over? board)))

(defmethod loop-game-play :tui [state]
  (printables/print-formatted (:printables state))
  (let [selection (get-selection state) updated-state (next-state state selection)]
    (if (game-over? updated-state)
      (get-play-again updated-state nil)
      (recur updated-state))))

(defn start-game [state]
  (if (= :tui (:ui state)) (printables/print-formatted [printables/title]))
  (let [state (assoc state :printables printables/player-x-printables :menu? true)]
    (loop-game-play state)))
