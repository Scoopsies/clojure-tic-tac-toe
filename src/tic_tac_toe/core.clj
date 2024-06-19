(ns tic-tac-toe.core)

;     ->board
(defn generate-board []
  (range 9))

(defn update-board [player selection board]
  (map #(if (= selection %) player %) board))

(defn get-available-moves [board]
  (filter (partial number?) board))