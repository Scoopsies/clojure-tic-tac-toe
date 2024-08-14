(ns tic-tac-toe.moves.easy
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.core :as moves-core]))

(defmethod moves-core/pick-move :easy [{:keys [board]}]
  (board/get-random-available board))