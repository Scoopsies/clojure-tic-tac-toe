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

(defn get-play-again [board settings]
  (printables/print-board board)
  (printables/print-win-lose-draw board settings)
  (if (play-again?)
    (play-game)
    (println (str "See you next time!"))))

(defn- get-move-choice [board settings]
  (printables/print-get-move-screen board settings)
  (let [active-player (core/find-active-player board)
        {:keys [move-fn]} (settings active-player)]
    (play-game (move-fn board) settings)))

(defn play-game
  ([]
   (printables/print-title)
   (play-game (range 9) (settings/get-all-settings)))

  ([board settings]
   (if (board/game-over? board)
     (get-play-again board settings)
     (get-move-choice board settings))))