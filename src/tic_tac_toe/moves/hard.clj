(ns tic-tac-toe.moves.hard
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.core :as move-core]))

(declare mini-max)

(defn- min-max-move
  ([board maximizer? depth]
   (if maximizer?
     (min-max-move board maximizer? depth (core/get-available-moves board) -100)
     (min-max-move board maximizer? depth (core/get-available-moves board) 100)))

  ([board maximizer? depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (core/update-board move board) (not maximizer?) (inc depth))]
       (if maximizer?
         (recur board maximizer? depth (rest moves) (max best-score score))
         (recur board maximizer? depth (rest moves) (min best-score score)))))))

(def min-max-move (memoize min-max-move))

(defn- score-game [board maximizer? depth]
  (let [player (core/find-active-player board)]
    (cond
      (board/win? player board) (if maximizer? (- 1000 depth) (+ -1000 depth))
      (board/win? (core/switch-player player) board) (if maximizer? (+ -1000 depth) (- 1000 depth))
      :else 0)))

(defn- mini-max [board maximizer? depth]
  (if (or (board/game-over? board) (> depth 6))
    (score-game board maximizer? depth)
    (min-max-move board maximizer? depth)))

(def mini-max (memoize mini-max))

;(defmethod get-default-moves :4x4 [])
;{#{5} 5
; #{6} 6
; []]
(defn get-default-move [moves]
  (cond
    (and (some #(= 5 %) moves) (not (some #(= 0 %) moves))) 5
    (and (some #(= 6 %) moves) (not (some #(= 3 %) moves))) 6
    (and (some #(= 9 %) moves) (not (some #(= 12 %) moves))) 9
    (and (some #(= 10 %) moves) (not (some #(= 15 %) moves))) 10
    (and (some #(= 0 %) moves) (not (some #(= 5 %) moves))) 0
    (and (some #(= 3 %) moves) (not (some #(= 6 %) moves))) 3
    (and (some #(= 12 %) moves) (not (some #(= 9 %) moves))) 12
    (and (some #(= 15 %) moves) (not (some #(= 10 %) moves))) 15
    :else (first moves)))

(defn- get-best-move [board moves]
  (let [moves-board (map #(core/update-board % board) moves)
        move-scores (map #(mini-max % false 0) moves-board)
        move-score-map (zipmap move-scores moves)]
    (->>
      (keys move-score-map)
      (sort >)
      (first)
      (move-score-map))))

(defmulti pick-move (fn [board] (count board)))

(defmethod pick-move :default [board]
  (let [moves (core/get-available-moves board)]
    (if (> (count moves) 12)
      (get-default-move moves)
      (get-best-move board moves))))

(defmethod pick-move 27 [board]
  (let [player (core/find-active-player board)
        available-moves (core/get-available-moves board)]
    (cond
      (move-core/win-next-turn? player board) (move-core/take-win player board)
      (move-core/lose-next-turn? player board) (move-core/take-block player board)
      (some #{13} available-moves) 13
      :else (rand-nth available-moves))))

(def pick-move (memoize pick-move))

(defn update-board-hard [board]
  (core/update-board (pick-move board) board))

(def update-board-hard (memoize update-board-hard))