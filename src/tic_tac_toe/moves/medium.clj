(ns tic-tac-toe.moves.medium
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as moves-core]))

(defmulti pick-medium-move :board-size)

(defmethod pick-medium-move :default [state]
  (let [board (:board state)
        active-player (core/get-active-player board)
        available-moves (core/get-available-moves board)]
    (cond
      (moves-core/win-next-turn? active-player board) (moves-core/take-win active-player board)
      (moves-core/lose-next-turn? active-player board) (moves-core/take-block active-player board)
      :else (first available-moves))))

(defmethod pick-medium-move :3x3x3 [state]
  (let [board (:board state)
        active-player (core/get-active-player board)
        available-moves (core/get-available-moves board)]
    (cond
      (moves-core/lose-next-turn? active-player board) (moves-core/take-block active-player board)
      (some #{13} board) 13
      :else (rand-nth available-moves))))

(defmethod moves-core/pick-move :medium [state]
  (pick-medium-move state))