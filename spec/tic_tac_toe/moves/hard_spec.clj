(ns tic-tac-toe.moves.hard-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.hard :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "hard"
  (context "min-max-move"
    (it "scores a win for maximizer"
      (should= 999 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] true 0)))

    (it "scores a win for minimizer"
      (should= -999 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] false 0)))

    (it "scores a Draw for maximizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] true 0)))

    (it "scores a Draw for minimizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] false 0)))
    )

  (context "pick-move"
    (context "3x3"
      (it "takes a win over a block"
        (should= 2 (sut/pick-move ["O" "O" 2 "X" "X" 5 "X" 7 8]))
        (should= 0 (sut/pick-move [0 1 "X" "O" 4 "X" "O" "X" 8])))

      (it "takes a block if no win is available"
        (should= 2 (sut/pick-move ["X" "X" 2 3 "O" 5 6 7 8]))
        (should= 8 (sut/pick-move [0 1 2 3 "O" 5 "X" "X" 8])))

      (it "plays the middle if corner is played first"
        (doseq [x [0 2 6 8]]
          (should= 4 (sut/pick-move (helper/populate-board "X" [x] (range 9))))))

      (it "plays the corner if middle is played first"
        (should= 8 (sut/pick-move [0 1 2 3 "X" 5 6 7 8])))
      )

    (context "3x3x3"
      (with-stubs)
      (it "takes a win if available"
        (let [x-board (helper/populate-board "X" [0 1] (range 27))
              full-board (helper/populate-board "O" [3 5] x-board)]
          (should= 2 (sut/pick-move full-board))))

      (it "takes a block if no win available."
        (let [x-board (helper/populate-board "X" [0 13] (range 27))
              full-board (helper/populate-board "O" [5] x-board)]
          (should= 26 (sut/pick-move full-board))))

      (it "takes the center if no win or block available"
        (should= 13 (sut/pick-move (range 27))))

      (it "makes a random move if no win, block, or center avaialble"
        (with-redefs [rand-nth (stub :rand-nth {:invoke first})]
          (let [board (helper/populate-board "X" [13] (range 27))]
            (should= 0 (sut/pick-move board)))))
      )
    )
  )