(ns tic-tac-toe.moves.medium
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as moves-core]))

(defn- get-medium-move [board]
  (let [active-player (core/find-active-player board)]
    (cond
      (moves-core/win-next-turn? active-player board) (moves-core/take-win active-player board)
      (moves-core/win-next-turn? (core/switch-player active-player) board) (moves-core/take-block active-player board)
      :else (first (core/get-available-moves board)))))

(def get-medium-move (memoize get-medium-move))

(defn update-board-medium [board]
  (core/update-board (get-medium-move board) board))