(ns tic-tac-toe.core)

(defn generate-board []
  (range 9))

(defn update-board [player selection board]
  (map #(if (= selection %) player %) board))

(defn available-moves [board]
  (filter (partial number?) board))



