(ns tic-tac-toe.spec-helper
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]))

(defn populate-board
  ([player-token positions board]
   (reduce #(core/update-board player-token %2 %1) board positions))

  ([player-token positions-1 positions-2 board]
   (let [board (populate-board player-token positions-1 board)
         player-token (core/switch-player player-token)]
     (populate-board player-token positions-2 board))))