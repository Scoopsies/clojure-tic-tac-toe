(ns tic-tac-toe.moves.core
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn take-win
  ([player-token board] (take-win player-token board (core/get-available-moves board)))

  ([player-token board moves]
   (let [move (first moves)
         updated-board (core/update-board player-token move board)
         winning-move? (or (empty? moves) (board/win? player-token updated-board))]
     (if winning-move?
       move
       (recur player-token board (rest moves))))))

(defn take-block [player-token board]
  (let [player-token (core/switch-player player-token)
        moves (core/get-available-moves board)]
    (take-win player-token board moves)))

(defn win-next-turn? [player-token board]
  (let [moves (core/get-available-moves board)
        next-move-wins (filter #(board/win? player-token (core/update-board player-token % board)) moves)]
    (not-empty next-move-wins)))

(defn lose-next-turn? [player-token board]
  (win-next-turn? (core/switch-player player-token) board))