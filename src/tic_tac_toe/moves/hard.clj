(ns tic-tac-toe.moves.hard
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.core :as move-core]
            [tic-tac-toe.moves.mini-max :as mini-max]))

(defmulti pick-hard-move :board-size)

(defmethod pick-hard-move :default [state]
  (mini-max/get-best-move state))

(defmethod pick-hard-move :3x3x3 [{:keys [board]}]
  (cond
    (board/win-next-turn? board) (move-core/take-win board)
    (board/lose-next-turn? board) (move-core/take-block board)
    (board/middle-available? board) (board/get-middle board)
    :else (board/get-random-available board)))

(defmethod move-core/pick-move :hard [state]
  (pick-hard-move state))