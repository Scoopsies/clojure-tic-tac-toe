(ns tic-tac-toe.moves.easyc
  (:require [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as moves-core]))

(defmethod moves-core/pick-move :easy [{:keys [board]}]
  (board/get-random-available board))