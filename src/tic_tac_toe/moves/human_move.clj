(ns tic-tac-toe.moves.human-move
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.printables :as printables]))

(defn- valid-move? [valid-moves player-move]
  (some #(= player-move %) valid-moves))

(defn get-human-move [board]
  (let [player-move (read-line)
        valid-moves (map #(str (inc %)) (core/get-available-moves board))]
    (if (valid-move? valid-moves player-move)
      (dec (Integer/parseInt player-move))
      (do
        (printables/print-valid-move-error player-move)
        (recur board)))))

(defn update-board-human [board]
  (core/update-board (get-human-move board) board))