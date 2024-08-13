(ns tic-tac-toe.moves.easy
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as moves-core]))

(defn update-board-easy [board]
  (let [available-moves (core/get-available-moves board)]
    (board/update-board (rand-nth available-moves) board)))

(defn get-easy-move [board]
  (let [available-moves (core/get-available-moves board)]
    (rand-nth available-moves)))

(defmethod moves-core/pick-move :easy [state]
  (get-easy-move (:board state)))