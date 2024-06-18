(ns tic-tac-toe.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.min-max :as sut]))


(describe "mini-max"
  (context "get-best-move"
    (it "takes a win over a block"
      (should= 2 (sut/get-best-move "O" ["O" "O" 2
                                         "X" "X" 5
                                         6 7 8]))
      (should= 0 (sut/get-best-move "O" [0 1 "X" "O" "O" "X" "O" 7 8]))
      )

    (it "takes a block if no win is available"
      (should= 2 (sut/get-best-move "O" ["O" "O" 2 3 "X" 5 6 7 8]))
      (should= 5 (sut/get-best-move "O" [0 1 2 "O" "O" 5 6 7 8])))

    (it "plays the middle if corner is played first"
      (should= 4 (sut/get-best-move "O" ["X" 1 2 3 4 5 6 7 8])))
    )

 (context "minimize"
    (it "scores a draw game"
      (should= 0 (sut/minimize "O" ["X" "O" "X"
                                    "O" "X" "O"
                                    "O" "X" 8] 0)))
    (it "scores a human win game"
      (should= -9 (sut/minimize "X" ["X" "O" "X"
                                    "O" "X" "O"
                                    "O" "X" 8] 0)))

    (it "scores a computer win game"
      (should= 9 (sut/minimize "O" [0 "O" "O"
                                      "O" "X" "O"
                                      "O" "X" "X"] 0)))
    )

  (context "maximize"
    (it "scores a draw game"
      (should= 0 (sut/maximize "O" ["X" "O" "X"
                                    "O" "X" "O"
                                    "O" "X" 8] 0))
      )

    (it "scores a computer win game"
      (should= 9 (sut/maximize "O" [0 1 "X"
                                    "O" "O" "X"
                                    "O" 7 8] 0)))

    (it "scores a human win game"
      (should= -9 (sut/minimize "X" ["X" "O" "X"
                                     "O" "X" "O"
                                     "O" "X" 8] 0)))
    ))

