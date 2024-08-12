(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "board"
  (context "rotate-plane-x"
    (it "makes a sideways plane"
      (let [plane-0 [24 15 6 21 12 3 18 9 0]
            plane-1 (map inc plane-0)
            plane-2 (map inc plane-1)]
        (should= plane-0 (sut/rotate-plane-x (range 27) 0))
        (should= plane-1 (sut/rotate-plane-x (range 27) 1))
        (should= plane-2 (sut/rotate-plane-x (range 27) 2)))))

  (context "rotate-cube-x"
    (it "makes a sideways board"
      (should= [2 11 20 5 14 23 8 17 26 1 10 19 4 13 22 7 16 25 0 9 18 3 12 21 6 15 24]
               (sut/rotate-cube-x (range 27)))
      (should= [2 11 20 5 14 23 8 17 26 1 10 19 4 13 22 7 16 25 0 9 "X" 3 12 21 6 15 24]
               (sut/rotate-cube-x (helper/populate-board "X" [18] (range 27))))))

  (context "rotate-plane-y"
    (context "3x3"
      (it "returns a 90 degree rotation of the board"
        (should= [2 5 8 1 4 7 0 3 6] (sut/rotate-plane-y (range 9)))
        (should= [11 14 17 10 13 16 9 12 15] (sut/rotate-plane-y (drop 9 (range 18))))
        (should= [20 23 26 19 22 25 18 21 24] (sut/rotate-plane-y (drop 18 (range 27))))))

    (context "4x4"
      (it "returns a 90 degree rotation of the board"
        (should= [3 7 11 15 2 6 10 14 1 5 9 13 0 4 8 12] (sut/rotate-plane-y (range 16)))))
    )

  (context "rotate-cube-y"
    (it "returns whole board rotated 90 degrees on the y axis"
      (should= [2 5 8 1 4 7 0 3 6 11 14 17 10 13 16 9 12 15 20 23 26 19 22 25 18 21 24]
               (sut/rotate-cube-y (range 27)))))

  (context "row-size"
    (it "returns 3 on 3x3 board"
      (should= 3 (sut/get-row-size (range 9))))

    (it "returns 4 on 4x4 board"
      (should= 4 (sut/get-row-size (range 16))))
    )

  (context "win? - 2d"
    (context "rows"
      (it "matching 3 horizontals is a win on 3 x 3 board"
        (should (sut/win? "X" (helper/populate-board "X" [0 1 2] (range 9))))
        (should (sut/win? "X" (helper/populate-board "X" [3 4 5] (range 9))))
        (should (sut/win? "X" (helper/populate-board "X" [6 7 8] (range 9)))))

      (it "matching 4 horizontals is a win on 4 x 4 board"
        (should (sut/win? "X" (helper/populate-board "X" [0 1 2 3] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [4 5 6 7] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [8 9 10 11] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [12 13 14 15] (range 16)))))

      (it "3x3 win doesn't count for 4x4 board"
        (should-not (sut/win? "X" (helper/populate-board "X" [0 1 2] (range 16))))
        (should-not (sut/win? "X" (helper/populate-board "X" [3 4 5] (range 16))))
        (should-not (sut/win? "X" (helper/populate-board "X" [6 7 8] (range 16)))))
      )

    (context "columns"
      (it "Matching 3 verticals is a win on a 3x3 board"
        (should (sut/win? "X" (helper/populate-board "X" [0 3 6] (range 9))))
        (should (sut/win? "X" (helper/populate-board "X" [1 4 7] (range 9))))
        (should (sut/win? "X" (helper/populate-board "X" [2 5 8] (range 9)))))

      (it "matching 4 horizontals is a win on 4 x 4 board"
        (should (sut/win? "X" (helper/populate-board "X" [0 4 8 12] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [1 5 9 13] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [2 6 10 14] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [3 7 11 15] (range 16)))))

      (it "3x3 win doesn't count for 4 x 4 board"
        (should-not (sut/win? "X" (helper/populate-board "X" [0 3 6] (range 16))))
        (should-not (sut/win? "X" (helper/populate-board "X" [1 4 7] (range 16))))
        (should-not (sut/win? "X" (helper/populate-board "X" [2 5 8] (range 16)))))

      )

    (context "diagonal"
      (it "matching diagonals are a win for 3 x 3"
        (should (sut/win? "X" (helper/populate-board "X" [0 4 8] (range 9))))
        (should (sut/win? "X" (helper/populate-board "X" [2 4 6] (range 9)))))

      (it "matching diagonals are a win for 4 x 4"
        (should (sut/win? "X" (helper/populate-board "X" [0 5 10 15] (range 16))))
        (should (sut/win? "X" (helper/populate-board "X" [3 6 9 12] (range 16)))))

      (it "3x3 win doesn't count for 4 x 4 board"
        (should-not (sut/win? "X" (helper/populate-board "X" [0 4 8] (range 16))))
        (should-not (sut/win? "X" (helper/populate-board "X" [2 4 6] (range 16)))))
      )
    )

  (context "win? 3-D"
    (context "forward-facing"
      (context "rows"
        (it "matching any row on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [0 1 2] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [3 4 5] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [6 7 8] (range 27))))
          )

        (it "matching any row on plane 1 is a win."
          (should (sut/win? "X" (helper/populate-board "X" [9 10 11] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [12 13 14] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [15 16 17] (range 27)))))

        (it "matching any row on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [18 19 20] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [21 22 23] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [24 25 26] (range 27)))))
        )

      (context "columns"
        (it "matching any column on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [0 3 6] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [1 4 7] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [2 5 8] (range 27)))))

        (it "matching any column on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [9 12 15] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [10 13 16] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [11 14 17] (range 27)))))

        (it "matching any column on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [18 21 24] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [19 22 25] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [20 23 26] (range 27)))))
        )

      (context "diagonals"
        (it "matching any diagonal on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [0 4 8] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [2 4 6] (range 27)))))

        (it "matching any diagonal on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [9 13 17] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [15 13 11] (range 27)))))

        (it "matching any diagonal on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [18 22 26] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [24 22 20] (range 27)))))

        (it "matching diagonal 'skewers' count as a win"
          (should (sut/win? "X" (helper/populate-board "X" [0 13 26] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [2 13 24] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [6 13 20] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [8 13 18] (range 27)))))
        )
      )

    (context "side-ways facing"
      (context "rows"
        (it "matching row on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [18 9 0] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [21 12 3] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [24 15 6] (range 27)))))

        (it "matching row on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [19 10 1] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [22 13 4] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [22 13 4] (range 27)))))

        (it "matching row on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [20 11 2] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [23 14 5] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [26 17 8] (range 27)))))
        )

      (context "diagonals"
        (it "matching diagonal on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [18 12 6] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [0 12 24] (range 27)))))

        (it "matching diagonal on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [19 13 7] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [1 13 25] (range 27)))))

        (it "matching diagonal on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [20 14 8] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [2 14 26] (range 27)))))
        )

      (context "columns"
        (it "matching columns on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [2 5 8] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [11 14 17] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [20 23 26] (range 27)))))

        (it "matching columns on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [1 10 19] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [10 13 16] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [19 22 25] (range 27)))))

        (it "matching columns on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [0 3 6] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [9 12 15] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [18 21 24] (range 27)))))
        )
      )

    (context "bottom-up facing"
      (context "rows"
        (it "matching rows on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [8 17 26] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [7 16 25] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [6 15 24] (range 27)))))

        (it "matching rows on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [5 14 23] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [4 13 22] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [3 12 21] (range 27)))))

        (it "matching rows on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [2 11 20] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [1 10 19] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [0 9 18] (range 27)))))
        )

      (context "diagonals"
        (it "matching diagonals on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [8 16 24] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [26 16 6] (range 27)))))

        (it "matching diagonals on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [5 13 21] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [23 13 3] (range 27)))))

        (it "matching diagonals on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [2 10 18] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [20 10 0] (range 27)))))
        )

      (context "columns"
        (it "matching columns on plane 0 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [8 7 6] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [17 16 15] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [26 25 24] (range 27)))))

        (it "matching columns on plane 1 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [5 4 3] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [14 13 12] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [23 22 21] (range 27)))))

        (it "matching columns on plane 2 is a win"
          (should (sut/win? "X" (helper/populate-board "X" [2 1 0] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [11 10 9] (range 27))))
          (should (sut/win? "X" (helper/populate-board "X" [20 19 18] (range 27)))))
        )
      )

    (context "no-moves?"
      (it "checks if theres no available moves on 2d board"
        (should (sut/no-moves? ["X" "O" "O" "O" "X" "X" "X" "X" "O"]))
        (should (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" "O" "X"]))
        (should (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" "O" "X"]))
        (should-not (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" 7 "X"]))
        )

      (it "checks if there is no available moves on a 3d board"
        (should (sut/no-moves? (repeat 27 "X")))
        (should-not (sut/no-moves? (conj (repeat 27 "X") 0))))
      )

    (context "Draw?"
      (it "returns false if x has won"
        (should-not (sut/draw? ["X" "X" "X" "O" "O" 5 6 7 8])))

      (it "returns false if o has won"
        (should-not (sut/draw? ["O" "O" "O" "X" "X" 5 "X" 7 8])))

      (it "returns false if game isn't over"
        (should-not (sut/draw? (range 9))))

      (it "returns true if no moves left, and no winners"
        (should (sut/draw? ["X" "X" "O" "O" "O" "X" "X" "O" "X"])))
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
  )