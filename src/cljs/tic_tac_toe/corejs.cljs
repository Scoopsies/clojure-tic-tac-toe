(ns tic-tac-toe.corejs
  (:require [tic-tac-toe.play-gamec :as game]))

(defn update-state [selection state]
  (reset! state (game/get-next-state @state selection)))