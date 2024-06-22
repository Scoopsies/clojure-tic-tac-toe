(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as end-game]
            [tic-tac-toe.core :as core]))


(defn print-valid-move-error [player-move]
  (println (str player-move " is not a valid move"))
  (println "Please enter a valid move."))

(defn print-row [row-n board]
  (let [inc-board (map #(if (number? %) (inc %) %) board)
        row-values (nth (partition 3 inc-board) (dec row-n))]
    (println " " (nth row-values 0) " | " (nth row-values 1) " | " (nth row-values 2))))

(defn print-board [board]
  (print-row 1 board)
  (print-row 2 board)
  (print-row 3 board))

(defn print-win-lose-draw [player-name board player-token]
  (cond
    (end-game/win? player-token board) (println (str "You win! Good Job " player-name "!"))
    (end-game/win? (core/switch-token player-token) board) (println (str "You lose. Better luck next-time " player-name "."))
    (end-game/no-moves? board) (println (str "Draw. You almost had it " player-name "."))))