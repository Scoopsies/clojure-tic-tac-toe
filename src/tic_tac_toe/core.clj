(ns tic-tac-toe.core)

(defn update-board [player selection board]
  (map #(if (= selection %) player %) board))

(defn get-available-moves [board]
  (filter (partial number?) board))