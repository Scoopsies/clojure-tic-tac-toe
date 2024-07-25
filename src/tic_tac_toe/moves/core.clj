(ns tic-tac-toe.moves.core
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn take-win
  ([player-token board] (take-win player-token board (core/get-available-moves board)))

  ([player-token board moves]
   (if (or (empty? moves) (board/win? player-token (core/update-board player-token (first moves) board)))
     (first moves)
     (recur player-token board (rest moves)))))

(defn take-block
  ([player-token board] (take-win (core/switch-player player-token) board (core/get-available-moves board))))

(defn win-next-turn? [player-token board]
  (let [moves (core/get-available-moves board)
        next-move-wins (filter #(board/win? player-token (core/update-board player-token % board)) moves)]
    (not (empty? next-move-wins))))