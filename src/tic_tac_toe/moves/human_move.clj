(ns tic-tac-toe.moves.human-move
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.core :as moves-core]))

(defn- valid-move? [valid-moves player-move]
  (some #(= player-move %) valid-moves))

(defn print-valid-move-error [player-move]
  (println (str player-move " is not a valid move"))
  (println "Please enter a valid move.")
  (println ""))

(defn get-human-move [board]
  (let [player-move (read-line)
        valid-moves (map #(str (inc %)) (board/get-available-moves board))]
    (if (valid-move? valid-moves player-move)
      (dec (Integer/parseInt player-move))
      (do
        (print-valid-move-error player-move)
        (recur board)))))

(defmethod moves-core/pick-move :human [state]
  (get-human-move (:board state)))