(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.settings :as settings]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printables :as printables]
            [clojure.string :as str]))

(declare play-game)

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
    (play-game {:ui :tui})
    (println "See you next time!")))

(defn- get-move-choice [state]
  (let [board (:board state)]
    (printables/print-board board)
    (printables/print-get-move board state)
    (let [active-player (core/get-active-player board)
          {:keys [move-fn]} (state active-player)
          updated-board (move-fn board)
          updated-state (assoc state :board updated-board)]
      (play-game updated-state))))

#_(defn get-all-settings [state]
  (printables/print-title)
  (let [x-settings (settings/get-player-settings "X" state)
        o-settings (settings/get-player-settings "O" state)
        board-size (settings/get-board state)
        board (board/create-board {:board-size board-size})]
    (assoc state
      "X" x-settings
      "O" o-settings
      :board-size board-size
      :board board)))

(defn xo-not-set [state]
  (or (not (state "X")) (not (state "O"))))

(defn play-game [state]
   (cond
     (not (:title state)) (play-game (printables/print-title state))
     (xo-not-set state) (play-game (settings/get-player-settings state))
     (not (:board-size state)) (play-game (settings/get-board state))
     (not (:board state)) (play-game (assoc state :board (board/create-board state)))
     (board/game-over? (:board state)) (get-play-again state)
     :else (get-move-choice state)))