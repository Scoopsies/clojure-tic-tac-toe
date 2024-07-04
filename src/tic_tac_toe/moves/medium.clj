(ns tic-tac-toe.moves.medium
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn take-win
  ([player board] (take-win player board (core/get-available-moves board)))

  ([player board moves]
   (if (or (empty? moves) (board/win? player (core/update-board player (first moves) board)))
     (first moves)
     (recur player board (rest moves)))))

(defn take-block
  ([player board] (take-win (core/switch-player player) board (core/get-available-moves board))))

(defn win-next-turn? [player board]
  (let [moves (core/get-available-moves board)
        next-move-wins (filter #(board/win? player (core/update-board player % board)) moves)]
    (not (empty? next-move-wins))))

(defn- get-medium-move [board]
  (let [active-player (core/find-active-player board)]
    (cond
      (win-next-turn? active-player board) (take-win active-player board)
      (win-next-turn? (core/switch-player active-player) board) (take-block active-player board)
      :else (first (core/get-available-moves board)))))

(def get-medium-move (memoize get-medium-move))

(defn update-board-medium [board]
  (core/update-board (get-medium-move board) board))