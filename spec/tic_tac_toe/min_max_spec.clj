(ns tic-tac-toe.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.min-max :as sut]))


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
      )

    #_(it "plays the opposite side of the board if a middle edge is played"
      (should= 7 (sut/get-best-move "O" [0 "X" 2 3 4 5 6 7 8]))
      (should= 5 (sut/get-best-move "O" [0 1 2 "X" 4 5 6 7 8]))
      (should= 1 (sut/get-best-move "O" [0 1 2 3 4 5 6 "X" 8]))
      (should= 3 (sut/get-best-move "O" [0 1 2 3 4 "X" 6 7 8])))

    (it "plays the corner if middle is played first"
      (should= 8 (sut/get-best-move  [0 1 2
                                         3 "X" 5
                                         6 7 8])))
    )

 (context "minimize"
    (it "scores a draw game"
      (should= 0 (sut/minimize "O" ["X" "O" "X" "O" "X" "O" "O" "X" 8] 0)))

   (it "scores a human win game"
      (should= -9 (sut/minimize "X" ["X" "O" "X" "O" "X" "O" "O" "X" 8] 0)))

    (it "scores a computer win game"
      (should= 9 (sut/minimize "O" [0 "O" "O" "O" "X" "O" "O" "X" "X"] 0)))
    )

  (context "maximize"
    (it "scores a draw game"
      (should= 0 (sut/maximize "O" ["X" "O" "X" "O" "X" "O" "O" "X" 8] 0)))

    (it "scores a computer win game"
      (should= 9 (sut/maximize "O" [0 1 "X" "O" "O" "X" "O" 7 8] 0)))

    (it "scores a human win game"
      (should= -9 (sut/minimize "X" ["X" "O" "X" "O" "X" "O" "O" "X" 8] 0)))
    )

  (context "find-active-player"
    (it "returns O if there are mores Xs on the board"
      (should= "O" (sut/find-active-player ["X" "X" "O"])))

    (it "returns X if there are more O's on the board"
      (should= "X" (sut/find-active-player ["O" "O" "X"])))

    (it "returns X if there are equal X's and O's"
      (should= "X" (sut/find-active-player ["X" "X" "O" "O"])))
    )
  )

