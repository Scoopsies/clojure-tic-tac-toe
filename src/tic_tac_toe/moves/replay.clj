(ns tic-tac-toe.moves.replay
  (:require [tic-tac-toe.moves.core :as moves-core]))

(defn get-replay-move [{:keys [replay-moves]}]
  (first replay-moves))

(defmethod moves-core/pick-move :replay [state]
  (get-replay-move state))