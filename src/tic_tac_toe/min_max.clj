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
           score (mini-max player (update-board player move board) true (inc depth))]
       (recur player board depth (rest moves) (min best-score score))))))

(defn maximize
  ([player board depth] (maximize player board depth (get-available-moves board) -100))

  ([player board depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max player (update-board player move board) false (inc depth))]
       (recur player board depth (rest moves) (max best-score score))))))

(defn- switch-player [player]
  (if (= player "X") "O" "X"))

(defn- score-game [board depth]
  (cond
    (win? "O" board) (- 10 depth)
    (win? "X" board) (+ -10 depth)
    :else 0))

(defn- mini-max [player board maximizer? depth]
  (let [player (switch-player player)]
    (if (game-over? board)
      (score-game board depth)
      (if maximizer?
        (maximize player board depth)
        (minimize player board depth)))))

(defn find-active-player [board]
  (let [amount-x (count (filter (partial = "X") board))
        amount-o (count (filter (partial = "O") board))]
    (if (<= amount-x amount-o) "X" "O")))

(defn get-best-move [board]
  (let [player (find-active-player board)
        moves (get-available-moves board)
        moves-board (map #(update-board player % board) moves)
        move-scores (map #(mini-max (find-active-player board) % false 0) moves-board)
        move-score-map (zipmap move-scores moves)]
    (->>
      (keys move-score-map)
      (sort >)
      (first)
      (move-score-map))))

