(ns tic-tac-toe.moves.easy-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.easy :as sut]))

(describe "easy"
  (with-stubs)
  (context "get-easy-board"
    (it "returns a random valid move on empty board"
      (with-redefs [rand-nth (stub :rand-nth {:return 0})]
        (should= ["X" 1 2 3 4 5 6 7 8] (sut/update-board-easy (range 9))))
      (with-redefs [rand-nth (stub :rand-nth {:return 6})]
        (should= [0 1 2 3 4 5 "X" 7 8] (sut/update-board-easy (range 9)))))

    (it "returns a random valid move on O's turn"
      (with-redefs [rand-nth (stub :rand-nth {:return 2})]
        (should= ["X" 1 "O" 3 4 5 6 7 8] (sut/update-board-easy ["X" 1 2 3 4 5 6 7 8])))
      (with-redefs [rand-nth (stub :rand-nth {:return 4})]
        (should= [0 1 2 3 "O" 5 "X" 7 8] (sut/update-board-easy [0 1 2 3 4 5 "X" 7 8]))))
    )
  )