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

(defn get-all-settings [state]
  (printables/print-title)
  (let [x-settings (settings/get-player-settings "X")
        o-settings (settings/get-player-settings "O")
        board-size (settings/get-board)
        board (board/create-board {:board-size board-size})]
    (assoc state
      "X" x-settings
      "O" o-settings
      :board-size board-size
      :board board)))

(defn play-game

  ([state]
   (cond
     (not (:board state)) (play-game (get-all-settings {:ui :tui}))
     (board/game-over? (:board state)) (get-play-again state)
     :else (get-move-choice state))))