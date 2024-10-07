(ns tic-tac-toe.moves.hardc
  (:require [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as move-core]
            [tic-tac-toe.moves.mini-maxc :as mini-max]))

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