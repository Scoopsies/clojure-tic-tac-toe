(ns tic-tac-toe.board
  (:require [tic-tac-toe.core :as core]))

(defn get-row-size [board]
  (int (Math/sqrt (count board))))

(defn ->grid [board]
  (partition (get-row-size board) board))

(defn rotate-plane-y [board]
  (let [row-size (get-row-size board)
        get-nth-row (fn [n board] (map #(nth % n) (map reverse (partition row-size board))))]
    (mapcat #(get-nth-row % board) (range row-size))))

(defn rotate-cube-y [board]
  (mapcat rotate-plane-y (partition 9 board)))

(defn rotate-plane-x [board plane]
  (let [get-nth-rotated-row (fn [n board plane] (map #(nth % (+ (* 3 n) plane)) (partition 9 board)))]
    (reverse (mapcat #(get-nth-rotated-row % board plane) (range 3)))))

(defn rotate-cube-x [board]
  (reverse (mapcat #(rotate-plane-x board %) (range 3))))

(defmulti match? (fn [_ board _] (count board)))

(defmethod match? :default [player-token _ positions]
  (some #(= (repeat 3 player-token) %) positions))

(defmethod match? 16 [player-token _ positions]
  (some #(= (repeat 4 player-token) %) positions))

(defmulti get-rows count)

(defmethod get-rows :default [board]
  (partition (get-row-size board) board))

(defmethod get-rows 27 [board]
  (mapcat get-rows (partition 9 board)))

(defmulti get-columns count)

(defmethod get-columns :default [board]
  (get-rows (rotate-plane-y board)))

(defmethod get-columns 27 [board]
  (get-rows (rotate-cube-y board)))

(defmulti get-diagonals (fn [board] (count board)))

(defmethod get-diagonals 9 [board]
  [(map #(nth board %) [0 4 8]) (map #(nth board %) [2 4 6])])

(defmethod get-diagonals 16 [board]
  [(map #(nth board %) [0 5 10 15]) (map #(nth board %) [3 6 9 12])])

(defn get-nth-plane [n board]
  (nth (partition 9 board) n))

(defmethod get-diagonals 27 [board]
  (let [x-plane-diagonals [(map #(nth board %) [0 13 26])
                           (map #(nth board %) [8 13 18])
                           (map #(nth board %) [2 13 24])
                           (map #(nth board %) [6 13 20])]]
    (concat (mapcat #(get-diagonals (get-nth-plane % board)) (range 3)) x-plane-diagonals)))

(defn row-match? [player-token board]
  (match? player-token board (get-rows board)))

(defmulti column-match? (fn [_ board] (count board)))

(defmethod column-match? :default [player-token board]
  (match? player-token board (get-columns board)))

(defmethod column-match? 27 [player-token board]
  (let [parted-board (partition 9 board)]
    (some #(column-match? player-token %) parted-board)))

(defn- diagonal-match? [player-token board]
  (match? player-token board (get-diagonals board)))

(defmulti win? (fn [_ board] (count board)))

(defmethod win? :default [player-token board]
  (or (column-match? player-token board)
      (diagonal-match? player-token board)
      (row-match? player-token board)))

(defmethod win? 27 [player-token board]
  (let [side-view (rotate-cube-x board)
        top-view (rotate-cube-x (rotate-cube-y board))
        check-for (fn [func] (some #(func player-token %) [board side-view top-view]))]
    (or
      (check-for row-match?)
      (check-for diagonal-match?)
      (check-for column-match?))))

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

(defmulti create-board :board-size)

(defmethod create-board :3x3 [_]
  (range 9))

(defmethod create-board :4x4 [_]
  (range 16))

(defmethod create-board :3x3x3 [_]
  (range 27))