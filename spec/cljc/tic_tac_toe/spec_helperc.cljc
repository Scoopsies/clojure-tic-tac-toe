(ns tic-tac-toe.spec-helperc
  (:require [tic-tac-toe.boardc :as board]
            [tic-tac-toe.corec :as core]))

(defn populate-board
  ([player-token positions board]
   (reduce #(board/update-board player-token %2 %1) board positions))

  ([player-token positions-1 positions-2 board]
   (let [board (populate-board player-token positions-1 board)
         player-token (core/switch-player player-token)]
     (populate-board player-token positions-2 board))))
