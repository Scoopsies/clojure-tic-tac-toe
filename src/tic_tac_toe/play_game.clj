(ns tic-tac-toe.play-game
  (:require [quil.core :as q]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.core :as move]
            [tic-tac-toe.moves.easy]
            [tic-tac-toe.moves.medium]
            [tic-tac-toe.moves.hard]
            [tic-tac-toe.moves.human-move]
            [tic-tac-toe.gui :as gui]
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

(defn get-play-again [state]
  (printables/print-board (:board state))
  (printables/print-win-lose-draw state)
  (if (play-again?)
    (start-game {:ui :tui})
    (println "See you next time!")))

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

(def board-size-menu ["What size board would you like to play on?"
                      "1. 3x3"
                      "2. 4x4"
                      "3. 3x3x3 (3-D)"])

(defn get-move-printables [board]
  (let [printable-board (printables/format-rows board)]
    (conj printable-board "" (str "Please pick a number 1-" (count board) "."))))

(defn get-move-x [state selection]
  (cond
    (= selection "1") (assoc state "X" {:move :human} :printables printables/player-o-printables)
    (= selection "2") (assoc state "X" {:move :easy} :printables printables/player-o-printables)
    (= selection "3") (assoc state "X" {:move :medium} :printables printables/player-o-printables)
    (= selection "4") (assoc state "X" {:move :hard} :printables printables/player-o-printables)
    :else state))

(defn get-move-o [state selection]
  (cond
    (= selection "1") (assoc state "O" {:move :human} :printables printables/board-size-menu)
    (= selection "2") (assoc state "O" {:move :easy} :printables printables/board-size-menu)
    (= selection "3") (assoc state "O" {:move :medium} :printables printables/board-size-menu)
    (= selection "4") (assoc state "O" {:move :hard} :printables printables/board-size-menu)
    :else state))

(defn- associate-board [state selection]
  (cond
    (= selection "1") (assoc state :board-size :3x3 :board (range 9))
    (= selection "2") (assoc state :board-size :4x4 :board (range 16))
    (= selection "3") (assoc state :board-size :3x3x3 :board (range 27))
    :else state))

(defn get-board [state selection]
  (let [updated-state (associate-board state selection)]
    (assoc updated-state :printables (get-move-printables (:board updated-state)))))

(defn make-move [state selection]
  (let [board (:board state)
        updated-board (core/update-board selection board)]
    (assoc state :board updated-board :printables (get-move-printables updated-board))))

(defn next-state [state selection]
  (cond
    (not (state "X")) (get-move-x state selection)
    (not (state "O")) (get-move-o state selection)
    (not (:board state)) (get-board state selection)
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
      (get-play-again updated-state)
      (recur updated-state))))

(defmethod loop-game-play :gui [_]
  (gui/start-gui))

(defn start-game [state]
  (if (= :tui (:ui state)) (printables/print-title))
  (let [state (assoc state :printables printables/player-x-printables)]
    (loop-game-play state)))

#_(defmethod loop-game-play :gui []
  (q/defsketch ...))