(ns tic-tac-toe.moves.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.core :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "moves-core"
  (context "win-next-turn?"
    (it "returns true for win from x in next turn"
      (should (sut/win-next-turn? "X" ["X" "X" 2 "O" "O" 5 6 7 8])))

    (it "returns true for win from o in next turn"
      (should (sut/win-next-turn? "O" ["X" 1 2 "O" "O" 5 "X" 7 8])))
    )

  (context "take-block"
    (it "tests"
      (should= 2 (sut/take-block "O" ["X" "X" 2 "O" 4 5 "O" 7 "X"]))))

  (context "take-block"
    (it "returns index of blocking move"
      (should= 2 (sut/take-block "X" ["O" "O" 2 "X" "X" 5 6 7 8]))))

  (context "take-win"
    (context "3x3"
      (it "takes any presented win"
        (let [board (range 9)
              test (fn [result played-spots]
                     (doseq [x played-spots]
                       (should= result (sut/take-win "X" (helper/populate-board "X" x board)))))]

          (test 0 [[1 2] [4 8] [3 6]])
          (test 1 [[0 2] [4 7]])
          (test 2 [[0 1] [4 6] [5 8]])

          (test 3 [[0 6] [4 5]])
          (test 4 [[0 8] [1 7] [2 6] [3 5]])
          (test 5 [[2 8] [3 4]])

          (test 6 [[0 3] [2 4] [7 8]])
          (test 7 [[1 4] [6 8]])
          (test 8 [[0 4] [2 5] [6 7]]))
        )
      )

    (context "4x4"
      (it "takes any presented win"
        (let [board (range 16)
              test (fn [result played-spots]
                     (doseq [x played-spots]
                       (should= result (sut/take-win "X" (helper/populate-board "X" x board)))))]

          (test 0 [[1 2 3] [4 8 12] [5 10 15]])
          (test 1 [[0 2 3] [5 9 13]])
          (test 2 [[0 1 3] [6 10 14]])
          (test 3 [[0 1 2] [6 9 12] [7 11 15]])

          (test 4 [[0 8 12] [5 6 7]])
          (test 5 [[1 9 13] [4 6 7]])
          (test 6 [[2 10 14] [4 5 7]])
          (test 7 [[3 11 15] [4 5 6]])

          (test 8 [[0 4 12] [9 10 11]])
          (test 9 [[1 5 13] [8 10 11]])
          (test 10 [[2 6 14] [8 9 11]])
          (test 11 [[3 7 15] [8 9 10]])

          (test 12 [[0 4 8] [3 6 9] [13 14 15]])
          (test 13 [[1 5 9] [12 14 15]])
          (test 14 [[2 6 10] [12 13 15]])
          (test 15 [[0 5 10] [3 7 11] [12 13 14]])))
      )
    )
  )