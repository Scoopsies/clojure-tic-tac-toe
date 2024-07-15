(ns tic-tac-toe.board
  (:require [tic-tac-toe.core :as core]))

(defn count-rows [board]
  (int (Math/sqrt (count board))))

(defmulti match? (fn [board _ _] (count board)))

(defmethod match? :default [board player-token positions]
  (some #(= (repeat (count-rows board) player-token) %) positions))

(defmethod match? 27 [_ player-token positions]
  (some #(= (repeat 3 player-token) %) positions))

(defn- get-rows [board]
  (partition (count-rows board) board))

(defn- get-columns
  ([board] (get-columns (partition (count-rows board) board) []))

  ([rows result]
   (if (empty? (first rows))
     result
     (recur (map rest rows) (cons (map first rows) result)))))

(defmulti get-diagonals (fn [board] (count board)))

(defmethod get-diagonals 9 [board]
  [(map #(nth board %) [0 4 8]) (map #(nth board %) [2 4 6])])

(defmethod get-diagonals 16 [board]
  [(map #(nth board %) [0 5 10 15]) (map #(nth board %) [3 6 9 12])])

(defmethod get-diagonals 27 [board]
  [(map #(nth board %) [0 4 5])
   (map #(nth board %) [9 13 17])
   (map #(nth board %) [18 22 26])])

(defmulti row-match? (fn [_ board] (count board)))

(defmethod row-match? :default [player-token board]
  (match? board player-token (get-rows board)))

(defmethod row-match? 27 [player-token board]
  (let [parted-board (partition 3 board)]
    (some #(row-match? player-token %) parted-board)))

(defmulti column-match? (fn [_ board] (count board)))

(defmethod column-match? :default [player-token board]
  (match? board player-token (get-columns board)))

(defmethod column-match? 27 [player-token board]
  (let [parted-board (partition 3 board)]
    (some #(column-match? player-token %) parted-board)))

(defn- diagonal-match? [player-token board]
  (match? board player-token (get-diagonals board)))

(defmulti win? (fn [_ board] (count board)))

(defmethod win? :default [player-token board]
  (or (column-match? player-token board)
      (diagonal-match? player-token board)
      (row-match? player-token board)))

(defn skewer-win? [player-token board corners]
  (= (repeat 3 player-token)
     [(nth (nth board 0) (nth corners 0))
      (nth (nth board 1) 4)
      (nth (nth board 2) (nth corners 1))]))

(defn diagonal-skewer? [player-token board]
  (let [parted-board (partition 3 board)]
    (or
      (skewer-win? player-token parted-board [0 8])
      (skewer-win? player-token parted-board [2 6])
      (skewer-win? player-token parted-board [6 2])
      (skewer-win? player-token parted-board [8 0]))))

(defn horizontal-skewer? [player-token board]
  (let [parted-board (partition 3 board)]
    (some #(= (repeat 3 player-token) %) (partition 3 (apply interleave parted-board)))))

(defmethod win? 27 [player-token board]
  (or (diagonal-match? player-token board)
      (row-match? player-token board)
      (column-match? player-token board)
      (horizontal-skewer? player-token board)
      (diagonal-skewer? player-token board)))

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