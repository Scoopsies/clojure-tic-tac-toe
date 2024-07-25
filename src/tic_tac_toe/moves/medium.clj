(ns tic-tac-toe.moves.medium
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as moves-core]))

(defmulti get-medium-move count)

(defmethod get-medium-move :default [board]
  (let [active-player (core/find-active-player board)
        available-moves (core/get-available-moves board)]
    (cond
      (moves-core/win-next-turn? active-player board) (moves-core/take-win active-player board)
      (moves-core/lose-next-turn? active-player board) (moves-core/take-block active-player board)
      :else (first available-moves))))

(defmethod get-medium-move 27 [board]
  (let [active-player (core/find-active-player board)
        available-moves (core/get-available-moves board)]
    (cond
      (moves-core/lose-next-turn? active-player board) (moves-core/take-block active-player board)
      (some #{13} board) 13
      :else (rand-nth available-moves))))

(def get-medium-move (memoize get-medium-move))

(defn update-board-medium [board]
  (core/update-board (get-medium-move board) board))