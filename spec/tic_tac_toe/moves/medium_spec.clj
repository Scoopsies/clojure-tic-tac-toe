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

  (context "take-block"
    (it "tests"
      (should= 2 (sut/take-block "O" ["X" "X" 2 "O" 4 5 "O" 7 "X"]))))

  (context "win-next-turn?"
    (it "returns true for win from x in next turn"
      (should (sut/win-next-turn? "X" ["X" "X" 2 "O" "O" 5 6 7 8])))

    (it "returns true for win from o in next turn"
      (should (sut/win-next-turn? "O" ["X" 1 2 "O" "O" 5 "X" 7 8])))
    )

  (context "take-block"
    (it "returns index of blocking move"
      (should= 2 (sut/take-block "X" ["O" "O" 2 "X" "X" 5 6 7 8]))))

  )