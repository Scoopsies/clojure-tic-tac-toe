(ns tic-tac-toe.moves.mini-max
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(declare mini-max)

(defn- min-max-move
  ([board maximizer? depth]
   (if maximizer?
     (min-max-move board maximizer? depth (board/get-available-moves board) -100)
     (min-max-move board maximizer? depth (board/get-available-moves board) 100)))

  ([board maximizer? depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (board/update-board move board) (not maximizer?) (inc depth))]
       (if maximizer?
         (recur board maximizer? depth (rest moves) (max best-score score))
         (recur board maximizer? depth (rest moves) (min best-score score)))))))

(def min-max-move (memoize min-max-move))

(defn- score-game [board maximizer? depth]
  (let [player (board/get-active-player board)]
    (cond
      (board/win? player board) (if maximizer? (- 1000 depth) (+ -1000 depth))
      (board/win? (core/switch-player player) board) (if maximizer? (+ -1000 depth) (- 1000 depth))
      :else 0)))

(defn- mini-max [board maximizer? depth]
  (if (or (board/game-over? board) (> depth 6))
    (score-game board maximizer? depth)
    (min-max-move board maximizer? depth)))

(def mini-max (memoize mini-max))

(defn get-default-move [moves]
  (let [default-pairs {5 0, 6 3, 9 12, 10 15, 0 5, 3 6, 12 9, 15 10}
        filter-fn (fn [[k v]] (and (some #{k} moves) (not (some #{v} moves))))
        default-move (first (filter filter-fn default-pairs))]
    (if default-move
      (first default-move)
      (first moves))))

(defn mini-max-move [{:keys [board]}]
  (let [moves (board/get-available-moves board)
        move-boards (map #(board/update-board % board) moves)
        move-scores (map #(mini-max % false 0) move-boards)
        move-score-map (zipmap move-scores moves)]
    (->>
      (keys move-score-map)
      (sort >)
      (first)
      (move-score-map))))

(defmulti get-best-move :board-size)

(defmethod get-best-move :3x3 [state]
  (mini-max-move state))

(defmethod get-best-move :4x4 [state]
  (let [{:keys [board]} state
        moves (board/get-available-moves board)]
    (if (> (count moves) 12)
      (get-default-move moves)
      (mini-max-move state))))