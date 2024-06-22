(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as sut]))

(describe "Tic Tac Toe"
  (context "update-board"
    (it "turns 0 into x"
      (should= ["X" 1 2 3 4 5 6 7 8] (sut/update-board "X" 0 (range 9))))

    (it "turns 2 into x"
      (should= [0 1 "O" 3 4 5 6 7 8] (sut/update-board "O" 2 (range 9))))
    )

  (context "available-moves"
    (it "returns all moves if all moves are available"
      (should= (range 9) (sut/get-available-moves (range 9))))

    (it "returns no moves if no moves are available"
      (should= [] (sut/get-available-moves ["X" "X" "O" "O" "X" "X" "O" "O" "X"])))

    (it "returns just the available moves"
      (should= [1 3 6] (sut/get-available-moves ["X" 1 "O" 3 "X" "X" 6 "O" "X"])))

    (it "returns available spaces"
      (should= [2 5 6 7 8] (sut/get-available-moves ["O" "O" 2 "X" "X" 5 6 7 8])))
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