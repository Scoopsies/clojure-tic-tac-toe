(ns tic-tac-toe.min-max
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]))

(declare mini-max)

(defn minimize
  ([player board depth] (minimize player board depth (get-available-moves board) 1000))

  ([player board depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (update-board player move board) true (inc depth))]
       (recur player board depth (rest moves) (min best-score score))))))

(defn maximize
  ([player board depth] (maximize player board depth (get-available-moves board) -1000))

  ([player board depth moves best-score]
   (if (empty? moves)
     best-score
     (let [move (first moves)
           score (mini-max (update-board player move board) false (inc depth))]
       (recur player board depth (rest moves) (max best-score score))))))

(defn- score-game [board depth]
  (cond
    (win? "X" board) (+ -10 depth)
    (win? "O" board) (- 10 depth)
    :else 0))

(defn- mini-max [board maximizer? depth]
  (if (game-over? board)
    (score-game board depth)
    (if maximizer?
      (maximize "O" board depth)
      (minimize "X" board depth))))

(defn get-best-move [player board]
  (loop [moves (get-available-moves board)
         best-move -1
         best-score -1000]
    (if (empty? moves)
      best-move
      (let [move (first moves)
            new-board (update-board player move board)
            score (if (= player "O")
                    (mini-max new-board false 0)
                    (mini-max new-board true 0))]
        (if (> score best-score)
          (recur (rest moves) move score)
          (recur (rest moves) best-move best-score))))))