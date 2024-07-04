(ns tic-tac-toe.board
  (:require [tic-tac-toe.core :as core]))

(defn- row-size [board]
  (int (Math/sqrt (count board))))

(defn-  match? [player-token board positions]
  (let [row-size (row-size board)]
    (= (repeat row-size player-token) (map #(nth board %) positions))))

(defn- horizontal-match? [player-token board]
  (let [row-size (row-size board)]
    (if (= row-size 3)
      (or (match? player-token board [0 1 2])
          (match? player-token board [3 4 5])
          (match? player-token board [6 7 8]))
      (or (match? player-token board [0 1 2 3])
          (match? player-token board [4 5 6 7])
          (match? player-token board [8 9 10 11])
          (match? player-token board [12 13 14 15])))))

(defn- diagonal-match? [player-token board]
  (let [row-size (row-size board)]
    (if (= row-size 3)
      (or (match? player-token board [0 4 8])
          (match? player-token board [2 4 6]))
      (or (match? player-token board [0 5 10 15])
          (match? player-token board [3 6 9 12])))))

(defn- vertical-match? [player-token board]
  (let [row-size (row-size board)]
    (if (= row-size 3)
      (or (match? player-token board [0 3 6])
          (match? player-token board [1 4 7])
          (match? player-token board [2 5 8]))
      (or (match? player-token board [0 4 8 12])
          (match? player-token board [1 5 9 13])
          (match? player-token board [2 6 10 14])
          (match? player-token board [3 7 11 15])))))

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