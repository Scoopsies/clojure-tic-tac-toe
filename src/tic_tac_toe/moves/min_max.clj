(ns tic-tac-toe.moves.min-max
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]))

(declare mini-max)

(defn- min-max-move
  ([board maximizer? depth]
   (if maximizer?
     (min-max-move board maximizer? depth (core/get-available-moves board) -100)
     (min-max-move board maximizer? depth (core/get-available-moves board) 100)))

  ([board maximizer? depth moves best-score]
   (let [player (core/find-active-player board)]
     (if (empty? moves)
       best-score
       (let [move (first moves)
             score (mini-max player (core/update-board player move board) (not maximizer?) (inc depth))]
         (if maximizer?
           (recur board maximizer? depth (rest moves) (max best-score score))
           (recur board maximizer? depth (rest moves) (min best-score score))))))))

(def min-max-move (memoize min-max-move))

(defn- switch-player [player]
  (if (= player "X") "O" "X"))

(defn- score-game [player board maximizer? depth]
  (cond
    (board/win? player board) (if maximizer? (- 10 depth) (+ -10 depth))
    (board/win? (switch-player player) board) (if maximizer? (+ -10 depth) (- 10 depth))
    :else 0))

(defn- mini-max [player board maximizer? depth]
  (let [player (switch-player player)]
    (if (board/game-over? board)
      (score-game player board maximizer? depth)
      (min-max-move board maximizer? depth))))

(def mini-max (memoize mini-max))

(defn- get-best-move [board]
  (let [player (core/find-active-player board)
        moves (core/get-available-moves board)
        moves-board (map #(core/update-board player % board) moves)
        move-scores (map #(mini-max player % false 0) moves-board)
        move-score-map (zipmap move-scores moves)]
    (->>
      (keys move-score-map)
      (sort >)
      (first)
      (move-score-map))))

(def get-best-move (memoize get-best-move))

(defn update-board-hard [board]
  (core/update-board (core/find-active-player board) (get-best-move board) board))
