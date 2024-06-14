(ns tic-tac-toe.end-game
  (:require [tic-tac-toe.core :as core]))

(defn- horizontal-match? [player board]
  (->> (partition 3 board)
       (filter #(= % (repeat 3 player)))
       (count)
       (zero?)
       (not)))

(defn- match? [player board positions]
  (= (repeat 3 player) (map #(nth board %) positions)))

(defn- diagonal-match? [player board]
  (or (match? player board [0 4 8])
      (match? player board [2 4 6])))

(defn- vertical-match? [player board]
  (or
    (match? player board [0 3 6])
    (match? player board [1 4 7])
    (match? player board [2 5 8])))

(defn win? [player board]
  (or
    (vertical-match? player board)
    (diagonal-match? player board)
    (horizontal-match? player board)))

(defn loss? [player board]
  (let [opposite-player (if (= player "X") "O" "X")]
    (or
      (horizontal-match? opposite-player board)
      (vertical-match? opposite-player board)
      (diagonal-match? opposite-player board)))
  )

(defn- board-full? [board]
  (empty? (core/available-moves board)))

(defn draw? [player board]
  (and
    (not (win? player board))
    (not (loss? player board))
    (board-full? board)))

(defn game-over? [player board]
  (or
    (win? player board)
    (loss? player board)
    (draw? player board)))