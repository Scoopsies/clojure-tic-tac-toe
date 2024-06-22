(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.min-max :as min-max]
            [tic-tac-toe.settings :as settings]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printables :as printables]
            [clojure.string :as str]))

(declare initialize-game)

(defn play-again? []
  (println "Would you like to play again? Y/N")
  (let [player-answer (str/lower-case (read-line))]
    (if (not-empty (filter #(= player-answer %) ["y" "yes" "no" "n"]))
      (or (= player-answer "yes")
          (= player-answer "y"))
      (recur))))

(defn print-game-over [player-name board player-token]
   (printables/print-board board)
      (printables/print-win-lose-draw player-name board player-token)
      (if (play-again?)
        (initialize-game player-name)
        (println (str "See you next time " player-name "!"))))

(defn- valid-move? [valid-moves player-move]
  (some #(= player-move %) valid-moves))

(defn get-player-move [board]
  (let [player-move (read-line)
        valid-moves (map #(str (inc %)) (core/get-available-moves board))]
    (if (valid-move? valid-moves player-move)
      (dec (Integer/parseInt player-move))
      (do
        (printables/print-valid-move-error player-move)
        (recur board)))))

(defn human-select [board player-name player-token]
  (printables/print-board board)
  (println (str "It's your turn " player-name ". Please pick a number 1-9."))
  (core/update-board player-token (get-player-move board) board))

(defn cpu-select [board player-token]
  (let [cpu-token (core/switch-token player-token)]
    (core/update-board cpu-token (min-max/get-best-move board) board)))

(defn play-as [current-player play-fn player-name board player-token]
  (if (= player-token current-player)
    (play-fn player-name (human-select board player-name player-token) player-token)
    (play-fn player-name (cpu-select board player-token) player-token)))

(defn play-game
  ([player-name] (play-game player-name (range 9) (settings/get-player-token)))

  ([player-name board player-token]
   (if (board/game-over? board)
     (print-game-over player-name board player-token)
     (play-as
       (core/find-active-player board) play-game player-name board player-token))))

(defn initialize-game
  ([]
   (initialize-game (settings/get-player-name)))

  ([player-name]
   (play-game player-name (range 9) (settings/get-player-token))))