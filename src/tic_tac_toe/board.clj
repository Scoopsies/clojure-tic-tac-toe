(ns tic-tac-toe.board
  (:require [tic-tac-toe.core :as core]))

(defn-  match? [player-token board positions]
  (= (repeat 3 player-token) (map #(nth board %) positions)))

(defn- horizontal-match? [player-token board]
  (or (match? player-token board [0 1 2])
      (match? player-token board [3 4 5])
      (match? player-token board [6 7 8])))

(defn- diagonal-match? [player-token board]
  (or (match? player-token board [0 4 8])
      (match? player-token board [2 4 6])))

(defn- vertical-match? [player-token board]
  (or (match? player-token board [0 3 6])
      (match? player-token board [1 4 7])
      (match? player-token board [2 5 8])))

(defn win? [player-token board]
  (or (vertical-match? player-token board)
      (diagonal-match? player-token board)
      (horizontal-match? player-token board)))

(defn no-moves? [board]
  (empty? (core/get-available-moves board)))

(defn draw? [board]
  (and
    (not (win? "X" board))
    (not (win? "O" board))
    (no-moves? board)))

(defn game-over? [board]
  (or
    (win? "X" board)
    (win? "O" board)
    (no-moves? board)))