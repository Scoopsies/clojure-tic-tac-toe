(ns tic-tac-toe.min-max
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]))

(declare mini-max)

(defn minimize
  ([player board depth] (minimize player board depth (get-available-moves board) 100))

  ([player board depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (update-board player move board) true (inc depth))]
       (recur player board depth (rest moves) (min best-score score))))))

(defn- score-game [board depth]
  (cond
    (win? "O" board) (- 10 depth)
    (win? "X" board) (+ -10 depth)
    :else 0))

(defn maximize
  ([player board depth] (maximize player board depth (get-available-moves board) -100))

  ([player board depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (update-board player move board) false (inc depth))]
       (recur player board depth (rest moves) (max best-score score))))))

(defn- mini-max [board maximizer? depth]
  (if (game-over? board)
    (score-game board depth)
    (if maximizer?
      (maximize "O" board depth)
      (minimize "X" board depth))))

(defn get-best-move [player board]
  (let [moves (get-available-moves board)
        moves-board (map #(update-board player % board) moves)
        move-scores (map #(mini-max % false 0) moves-board)
        move-score-map (zipmap move-scores moves)]
    (move-score-map (first (sort > (keys move-score-map))))))