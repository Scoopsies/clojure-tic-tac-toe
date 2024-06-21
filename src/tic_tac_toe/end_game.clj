(ns tic-tac-toe.end-game
  (:require [tic-tac-toe.core :as core]))

(defn-  match? [player board positions]
  (= (repeat 3 player) (map #(nth board %) positions)))

(defn- horizontal-match? [player board]
  (or (match? player board [0 1 2])
      (match? player board [3 4 5])
      (match? player board [6 7 8])))

(defn- diagonal-match? [player board]
  (or (match? player board [0 4 8])
      (match? player board [2 4 6])))

(defn- vertical-match? [player board]
  (or (match? player board [0 3 6])
      (match? player board [1 4 7])
      (match? player board [2 5 8])))

(defn win? [player board]
  (or (vertical-match? player board)
      (diagonal-match? player board)
      (horizontal-match? player board)))

(defn no-moves? [board]
  (empty? (core/get-available-moves board)))

(defn game-over? [board]
  (or
    (win? "X" board)
    (win? "O" board)
    (no-moves? board)))