(ns tic-tac-toe.moves.replay-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.replay :as sut]))

(describe "replay -move"
  (context "get-replay-move"
    (it "returns the first of the moves"
      (should= 0 (sut/get-replay-move {:replay-moves [0 1 2 3]}))
      (should= 1 (sut/get-replay-move {:replay-moves [1 2 3]})))
    )
  )