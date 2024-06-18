(ns tic-tac-toe.end-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.end-game :as sut]))

(describe "end-game"
  (context "win?"

    (it "matching horizontals are a win"
      (should (sut/win? "X" ["X" "X" "X" 3 4 5 6 7 8]))
      (should (sut/win? "X" ["X" "X" "X" 3 "O" 5 "O" 7 8]))
      (should (sut/win? "X" [0 1 2 "X" "X" "X" 6 7 8]))
      (should (sut/win? "X" [0 1 2 3 4 5 "X" "X" "X"]))
      )

    (it "matching Verticals are a win"
      (should (sut/win? "X" ["X" 1 2 "X" 4 5 "X" 7 8]))
      (should (sut/win? "X" [0 "X" 2 3 "X" 5 6 "X" 8]))
      (should (sut/win? "X" [0 1 "X" 3 4 "X" 6 7 "X"]))
      )

    (it "matching diagonals are a win"
      (should (sut/win? "X" ["X" 1 2 3 "X" 5 6 7 "X"]))
      (should (sut/win? "X" [0 1 "X" 3 "X" 5 "X" 7 8]))
      (should (sut/win? "X" ["X" "O" 2 3 "X" 5 6 7 "X"]))
      )
    )

  (context "lose?"
    (it "opposite player matching horizontals are a loss"
      (should (sut/loss? "X" ["O" "O" "O" 3 4 5 6 7 8]))
      (should (sut/loss? "X" [0 1 2 "O" "O" "O" 6 7 8]))
      (should (sut/loss? "X" [0 "X" 2 3 4 5 "O" "O" "O"]))
      )

    (it "opposite player matching Verticals are a loss"
      (should (sut/loss? "X" ["O" 1 2 "O" 4 5 "O" 7 8]))
      (should (sut/loss? "X" [0 "O" 2 3 "O" 5 6 "O" 8]))
      (should (sut/loss? "X" [0 1 "O" 3 4 "O" 6 7 "O"]))
      )

    (it "opposite player matching diagonals are a loss"
        (should (sut/loss? "X" ["O" 1 2 3 "O" 5 6 7 "O"]))
        (should (sut/loss? "X" [0 1 "O" 3 "O" 5 "O" 7 8]))
        (should (sut/loss? "X" ["O" "X" 2 3 "O" 5 6 7 "O"])))
    )

  (context "no-moves?"
    (it "checks if theres no available moves."
      (should (sut/no-moves? ["X" "O" "O" "O" "X" "X" "X" "X" "O"]))
      (should (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" "O" "X"]))
      (should-not (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" 7 "X"]))
      )
    )

  (context "game-over?"
    (it "returns true if draw"
      (should (sut/game-over? ["X" "O" "X" "O" "X" "O" "O" "X" "O"])))

    (it "returns true if win"
      (should (sut/game-over? ["O" 1 2 3 "O" 5 6 7 "O"])))

    (it "returns true if loss"
      (should (sut/game-over? [0 1 "O" 3 "O" 5 "O" 7 8])))

    (it "returns false if not win, loss, or draw."
      (should-not (sut/game-over? [0 1 2 3 "O" 5 "O" 7 8])))
    )
  )
