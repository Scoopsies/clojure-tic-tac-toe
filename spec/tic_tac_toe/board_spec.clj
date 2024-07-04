(ns tic-tac-toe.board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :as sut]
            [tic-tac-toe.core :as core]))

(defn populate-board [player-token positions board]
  (loop [positions positions
         board board]
    (if (empty? positions)
      board
      (recur (rest positions)
             (core/update-board player-token (first positions) board)))))

(describe "board"

  (context "win?"
    (context "horizontal"
      (it "matching 3 horizontals is a win on 3 x 3 board"
        (should (sut/win? "X" (populate-board "X" [0 1 2] (range 9))))
        (should (sut/win? "X" (populate-board "X" [3 4 5] (range 9))))
        (should (sut/win? "X" (populate-board "X" [6 7 8] (range 9)))))

      (it "matching 4 horizontals is a win on 4 x 4 board"
        (should (sut/win? "X" (populate-board "X" [0 1 2 3] (range 16))))
        (should (sut/win? "X" (populate-board "X" [4 5 6 7] (range 16))))
        (should (sut/win? "X" (populate-board "X" [8 9 10 11] (range 16))))
        (should (sut/win? "X" (populate-board "X" [12 13 14 15] (range 16)))))

      (it "3x3 win doesn't count for 4x4 board"
        (should-not (sut/win? "X" (populate-board "X" [0 1 2] (range 16))))
        (should-not (sut/win? "X" (populate-board "X" [3 4 5] (range 16))))
        (should-not (sut/win? "X" (populate-board "X" [6 7 8] (range 16)))))
      )

    (context "vertical"
      (it "matching 3 Verticals is a win for 3 x 3"
        (should (sut/win? "X" (populate-board "X" [0 3 6] (range 9))))
        (should (sut/win? "X" (populate-board "X" [1 4 7] (range 9))))
        (should (sut/win? "X" (populate-board "X" [2 5 8] (range 9)))))

      (it "matching 4 horizontals is a win on 4 x 4 board"
        (should (sut/win? "X" (populate-board "X" [0 4 8 12] (range 16))))
        (should (sut/win? "X" (populate-board "X" [1 5 9 13] (range 16))))
        (should (sut/win? "X" (populate-board "X" [2 6 10 14] (range 16))))
        (should (sut/win? "X" (populate-board "X" [3 7 11 15] (range 16)))))

      (it "3x3 win doesn't count for 4 x 4 board"
        (should-not (sut/win? "X" (populate-board "X" [0 3 6] (range 16))))
        (should-not (sut/win? "X" (populate-board "X" [1 4 7] (range 16))))
        (should-not (sut/win? "X" (populate-board "X" [2 5 8] (range 16)))))
      )

    (context "diagonal"
      (it "matching diagonals are a win for 3 x 3"
        (should (sut/win? "X" (populate-board "X" [0 4 8] (range 9))))
        (should (sut/win? "X" (populate-board "X" [2 4 6] (range 9)))))

      (it "matching diagonals are a win for 4 x 4"
        (should (sut/win? "X" (populate-board "X" [0 5 10 15] (range 16))))
        (should (sut/win? "X" (populate-board "X" [3 6 9 12] (range 16)))))

      (it "3x3 win doesn't count for 4 x 4 board"
        (should-not (sut/win? "X" (populate-board "X" [0 4 8] (range 16))))
        (should-not (sut/win? "X" (populate-board "X" [2 4 6] (range 16)))))
      )
    )

  (context "no-moves?"
    (it "checks if theres no available moves."
      (should (sut/no-moves? ["X" "O" "O" "O" "X" "X" "X" "X" "O"]))
      (should (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" "O" "X"]))
      (should-not (sut/no-moves? ["O" "X" "X" "X" "X" "O" "O" 7 "X"]))
      )
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
