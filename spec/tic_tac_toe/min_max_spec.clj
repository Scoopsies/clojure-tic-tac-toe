(ns tic-tac-toe.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.min-max :as sut]))


(describe "mini-max"
  (context "take-win"
    (it "takes a win when presented"
      (should= 2 (sut/take-win "O" ["O" "O" 2 3 4 5 6 7 8]))
      (should= 5 (sut/take-win "O" [0 1 2 "O" "O" 5 6 7 8])))
    )

  (context "take-block"
    (it "takes a blocking move"
      (should= 2 (sut/take-block "X" ["O" "O" 2 3 4 5 6 7 8]))
      (should= 5 (sut/take-block "X" [0 1 2 "O" "O" 5 6 7 8])))
    )

  (context "best-move"
    (it "takes a win over a block"
      (should= 5 (sut/best-move "X" ["O" "O" 2 "X" "X" 5 6 7 8]))
      (should= 0 (sut/best-move "O" [0 1 "X" "O" "O" "X" "O" 7 8]))))
  )