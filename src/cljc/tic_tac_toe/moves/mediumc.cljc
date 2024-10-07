(ns tic-tac-toe.moves.mediumc
  (:require [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as moves-core]))

(defmulti pick-medium-move :board-size)

(defmethod pick-medium-move :default [{:keys [board]}]
  (cond
    (board/win-next-turn? board) (moves-core/take-win board)
    (board/lose-next-turn? board) (moves-core/take-block board)
    :else (board/get-random-available board)))

(defmethod pick-medium-move :3x3x3 [{:keys [board]}]
  (cond
    (board/lose-next-turn? board) (moves-core/take-block board)
    (board/middle-available? board) (board/get-middle board)
    :else (board/get-random-available board)))

(defmethod moves-core/pick-move :medium [state]
  (pick-medium-move state))
