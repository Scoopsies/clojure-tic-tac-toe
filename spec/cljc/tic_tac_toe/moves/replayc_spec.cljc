(ns tic-tac-toe.moves.replayc-spec
  (:require [speclj.core :refer [describe redefs-around stub with-stubs context it should= should-have-invoked]]
            [tic-tac-toe.moves.replayc :as sut]))

(describe "replay -move"
  (with-stubs)
  (redefs-around [sut/thread-sleep (stub :sleep)])

  (context "get-replay-move"
    (it "returns the first of the moves"
      (should= 0 (sut/get-replay-move {:replay-moves [0 1 2 3]}))
      (should= 1 (sut/get-replay-move {:replay-moves [1 2 3]})))

    (it "invokes thread sleep to make a delay between moves"
      (sut/get-replay-move {:replay-moves [0 1 2 3]})
      (should-have-invoked :sleep))
    )
  )