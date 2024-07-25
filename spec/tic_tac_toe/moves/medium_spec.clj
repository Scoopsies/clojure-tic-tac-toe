(ns tic-tac-toe.moves.medium-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.medium :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "medium"
  (context "get-medium-move"
    (context "3x3"
      (it "returns index of winning move"
        (should= 2 (sut/get-medium-move ["X" "X" 2 "O" "O" 5 6 7 8]))
        (should= 5 (sut/get-medium-move ["X" "X" 2 "O" "O" 5 "X" 7 8])))

      (it "returns index of blocking move if winning move isn't available"
        (should= 2 (sut/get-medium-move ["X" "X" 2 "O" 4 5 "O" 7 "X"]))
        (should= 5 (sut/get-medium-move [0 "X" 2 "O" "O" 5 6 "X" 8])))

      (it "returns first available if can't win or block"
        (should= 0 (sut/get-medium-move (range 9)))
        (should= 1 (sut/get-medium-move ["X" 1 2 3 4 5 6 7 8])))
      )


    (context "3x3x3"
      (with-stubs)
      (redefs-around [rand-nth (stub :rand-nth {:invoke first})])
      (it "takes center if available"
        (should= 13 (sut/get-medium-move (range 27))))

      (it "takes block if presented"
        (let [board (helper/populate-board "X" [0 1] (range 27))]
          (should= 2 (sut/get-medium-move board)))
        (let [board (helper/populate-board "X" [24 25] (range 27))]
          (should= 26 (sut/get-medium-move board))))

      (it "randomizes move if center is taken and block isn't possible"
        (let [board (helper/populate-board "X" [13] (range 27))]
          (should= 0 (sut/get-medium-move board))))
      )
    )
  )