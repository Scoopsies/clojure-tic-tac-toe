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

  (context "get-best-move"
    (context "3x3"
      (it "takes a win over a block"
        (should= 2 (sut/pick-move ["O" "O" 2 "X" "X" 5 "X" 7 8]))
        (should= 0 (sut/pick-move [0 1 "X" "O" 4 "X" "O" "X" 8])))

      (it "takes a block if no win is available"
        (should= 2 (sut/pick-move ["X" "X" 2 3 "O" 5 6 7 8]))
        (should= 8 (sut/pick-move [0 1 2 3 "O" 5 "X" "X" 8])))

      (it "plays the middle if corner is played first"
        (should= 4 (sut/pick-move ["X" 1 2 3 4 5 6 7 8]))
        (should= 4 (sut/pick-move [0 1 "X" 3 4 5 6 7 8]))
        (should= 4 (sut/pick-move [0 1 2 3 4 5 "X" 7 8]))
        (should= 4 (sut/pick-move [0 1 2 3 4 5 6 7 "X"])))

      (it "plays the corner if middle is played first"
        (should= 8 (sut/pick-move [0 1 2 3 "X" 5 6 7 8])))
      )

    (context "3x3x3"
      (it "takes the center as first move as x"
        (should= 13 (sut/pick-move (range 27))))

      (it "takes the center as first move as O (if available)."
        (doseq [x (remove #{13} (range 27))]
          (should= 13 (sut/pick-move (helper/populate-board "X" [x] (range 27)))))))
    )

  (context "update-board-hard"
    (context "3x3x3"
      (it "updates the board to play 13 as its first move."
        (should= (helper/populate-board "X" [13] (range 27))
                 (sut/update-board-hard (range 27))))
      )
    )
  )