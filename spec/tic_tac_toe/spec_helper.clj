(ns tic-tac-toe.spec-helper
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]))

(defn populate-board [player-token positions board]
  (reduce #(core/update-board player-token %2 %1) board positions))