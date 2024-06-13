(ns tic-tac-toe.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.min-max :as sut]))


(context "score"
  (it "returns 0 for a draw"
    (should= 0 (sut/score "X" ["X" "O" "O" "O" "X" "X" "X" "X" "O"])))

  (it "returns 10 for a win"
    (should= 10 (sut/score "X" ["X" "X" "X" 3 4 5 6 7 8]))
    (should= 10 (sut/score "X" [0 1 2 "X" "X" "X" 6 7 8])))

  (it "returns -10 for a loss"
    (should= -10 (sut/score "X" ["O" "O" "O" 3 4 5 6 7 8])))
  )

(context "best-move"
  (it "returns winning move if available"
    (should= 4 (sut/best-move "X" ["X" "O" 2 "O" 4 5 "O" "O" "X"]))
    (should= 1 (sut/best-move "O" ["X" 1 "X" "O" "O" 4 "O" 5 "X" ]))))