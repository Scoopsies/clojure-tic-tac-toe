(ns tic-tac-toe.min-max
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]))

(declare mini-max)

(defn find-active-player [board]
  (let [amount-x (count (filter (partial = "X") board))
        amount-o (count (filter (partial = "O") board))]
    (if (<= amount-x amount-o) "X" "O")))

(defn min-max-move
  ([board maximizer? depth]
   (if maximizer?
     (min-max-move board maximizer? depth (get-available-moves board) -100)
     (min-max-move board maximizer? depth (get-available-moves board) 100)))

  ([board maximizer? depth moves best-score]
   (let [player (find-active-player board)]
     (if (empty? moves)
       best-score
       (let [move (first moves)
             score (mini-max player (update-board player move board) (not maximizer?) (inc depth))]
         (if maximizer?
           (recur board maximizer? depth (rest moves) (max best-score score))
           (recur board maximizer? depth (rest moves) (min best-score score))))))))

(defn- switch-player [player]
  (if (= player "X") "O" "X"))

(defn- score-game [player board maximizer? depth]
  (cond
    (win? player board) (if maximizer? (- 10 depth) (+ -10 depth))
    (win? (switch-player player) board) (if maximizer? (+ -10 depth) (- 10 depth))
    :else 0))

(defn- mini-max [player board maximizer? depth]
  (let [player (switch-player player)]
    (if (game-over? board)
      (score-game player board maximizer? depth)
      (min-max-move board maximizer? depth))))

(defn get-best-move [board]
  (let [player (find-active-player board)
        moves (get-available-moves board)
        moves-board (map #(update-board player % board) moves)
        move-scores (map #(mini-max player % false 0) moves-board)
        move-score-map (zipmap move-scores moves)]
    (->>
      (keys move-score-map)
      (sort >)
      (first)
      (move-score-map))))