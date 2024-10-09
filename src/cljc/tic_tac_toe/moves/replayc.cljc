(ns tic-tac-toe.moves.replayc
  (:require [tic-tac-toe.moves.corec :as moves-core]))

(defn thread-sleep []
  #?(:clj (Thread/sleep 1000)))

(defn get-replay-move [{:keys [replay-moves]}]
  (thread-sleep)
  (first replay-moves))

(defmethod moves-core/pick-move :replay [state]
  (get-replay-move state))