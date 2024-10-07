(ns tic-tac-toe.moves.mini-maxc-spec
  (:require [speclj.core :refer [describe context it should=]]
            [tic-tac-toe.moves.mini-maxc :as sut]))

(describe "mini-max"
  (describe "score-moves"
    (context "min-max-move"
      (it "scores a win for maximizer"
        (should= 999 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] true 0)))

      (it "scores a win for minimizer"
        (should= -999 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] false 0)))

      (it "scores a Draw for maximizer"
        (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] true 0)))

      (it "scores a Draw for minimizer"
        (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] false 0)))
      )))