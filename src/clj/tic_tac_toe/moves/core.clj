(ns tic-tac-toe.moves.core
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]))

(defn take-win
  ([board] (take-win (board/get-active-player board) board (board/get-available-moves board)))

  ([player-token board moves]
   (let [move (first moves)
         updated-board (board/update-board player-token move board)
         winning-move? (or (empty? moves) (board/win? player-token updated-board))]
     (if winning-move?
       move
       (recur player-token board (rest moves))))))

(defn take-block [board]
  (let [active-player (board/get-active-player board)
        player-token (core/switch-player active-player)
        moves (board/get-available-moves board)]
    (take-win player-token board moves)))

(defn get-move-param [state]
  (let [active-player (board/get-active-player (:board state))]
    (state active-player)))

(defmulti pick-move get-move-param)