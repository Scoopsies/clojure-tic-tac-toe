(ns tic-tac-toe.min-max
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]))

(defn score [player board]
  (cond
    (win? player board) 10
    (loss? player board) -10
    :else 0))

(defn best-move [player board]
  4)




