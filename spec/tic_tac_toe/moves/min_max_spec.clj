(ns tic-tac-toe.moves.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.min-max :as sut]))

(describe "mini-max"

  (context "get-best-move"
    (it "takes a win over a block"
      (should= 2 (sut/get-best-move ["O" "O" 2 "X" "X" 5 "X" 7 8]))
      (should= 0 (sut/get-best-move [0 1 "X" "O" 4 "X" "O" "X" 8])))

    (it "takes a block if no win is available"
      (should= 2 (sut/get-best-move ["X" "X" 2 3 "O" 5 6 7 8]))
      (should= 8 (sut/get-best-move [0 1 2 3 "O" 5 "X" "X" 8])))

    (it "plays the middle if corner is played first"
      (should= 4 (sut/get-best-move ["X" 1 2 3 4 5 6 7 8]))
      (should= 4 (sut/get-best-move [0 1 "X" 3 4 5 6 7 8]))
      (should= 4 (sut/get-best-move [0 1 2 3 4 5 "X" 7 8]))
      (should= 4 (sut/get-best-move [0 1 2 3 4 5 6 7 "X"]))
      )

    (it "plays the corner if middle is played first"
      (should= 8 (sut/get-best-move [0 1 2 3 "X" 5 6 7 8])))
    )

  (context "min-max-move"
    (it "scores a win for maximizer"
      (should= 9 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] true 0)))

    (it "scores a win for minimizer"
      (should= -9 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] false 0)))

    (it "scores a Draw for maximizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] true 0)))

    (it "scores a Draw for minimizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] false 0)))
    )
  )