(ns tic-tac-toe.moves.medium-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.medium :as sut]))

(describe "medium"
  (context "get-medium-move"
    (it "returns index of winning move"
      (should= 2 (sut/get-medium-move ["X" "X" 2 "O" "O" 5 6 7 8])))

    (it "returns index of blocking move if winning move isn't available"
      (should= 2 (sut/get-medium-move ["X" "X" 2 "O" 4 5 "O" 7 "X"])))
    )
  )