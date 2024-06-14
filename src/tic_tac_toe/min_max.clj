(ns tic-tac-toe.min-max
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]))

(defn switch-player [player]
  (if (= player "X") "O" "X"))

(defn take-win
  ([player board] (take-win player board (available-moves board)))

   ([player board moves]
    (if (win? player (update-board player (first moves) board))
      (first moves)
      (recur player board (rest moves)))))

(defn take-block
  ([player board] (take-block (switch-player player) board (available-moves board)))

  ([player board moves]
   (if (win? player (update-board player (first moves) board))
     (first moves)
     (recur player board (rest moves)))))

(defn win-next-turn? [player moves board]
  (not-empty (filter #(win? player (update-board player % board)) moves)))

(defn lose-next-turn? [player moves board]
  (not-empty (filter #(loss? player (update-board player % board)) moves)))

(defn best-move [player board]
  (let [moves (available-moves board)]
    (cond
      (win-next-turn? player moves board) (take-win player board)
      (lose-next-turn? player moves board) (take-block player board)
      :else nil
      )))









