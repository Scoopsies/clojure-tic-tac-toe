(ns tic-tac-toe.moves.easy
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as moves-core]))

(defn update-board-easy [board]
  (let [available-moves (core/get-available-moves board)]
    (core/update-board (rand-nth available-moves) board)))

(defmethod moves-core/pick-move :easy [state]
  (update-board-easy (:board state)))