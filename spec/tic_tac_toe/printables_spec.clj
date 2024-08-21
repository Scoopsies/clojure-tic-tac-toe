(ns tic-tac-toe.printables-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.printables :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "printables"
  (with-stubs)
  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= ["  1  |  2  |  3  "
                "  4  |  5  |  6  "
                "  7  |  8  |  9  "]
               (sut/get-board-printables (range 9))))

    (it "player tokens aren't effected by incrementation"
      (should= ["  1  |  2  |  3  "
                "  4  |  X  |  6  "
                "  7  |  8  |  9  "]
               (sut/get-board-printables (helper/populate-board "X" [4] (range 9)))))

    (it "prints a 4 x 4 board"
      (should= ["  1  |  2  |  3  |  4  "
                "  5  |  6  |  7  |  8  "
                "  9  |  10 |  11 |  12 "
                "  13 |  14 |  15 |  16 "]
               (sut/get-board-printables (range 16))))

    (it "prints a 3x3x3 board"
      (should= ["  1  |  2  |  3    10 |  11 |  12   19 |  20 |  21 "
                "  4  |  5  |  6    13 |  14 |  15   22 |  23 |  24 "
                "  7  |  8  |  9    16 |  17 |  18   25 |  26 |  27 "]
               (sut/get-board-printables (range 27))))
    )

  (context "get-board-size-menu"
    (it "returns correct menu for :tui"
      (should= ["What size board?"
                "1. 3x3"
                "2. 4x4"
                "3. 3x3x3 (3-D)"] (sut/get-board-size-menu {:ui :tui})))

    (it "returns correct menu for :gui"
      (should= ["What size board?"
                "1. 3x3"
                "2. 4x4"] (sut/get-board-size-menu {:ui :gui}))))

  (context "get-winner-printable"
    (it "returns X wins!"
      (should= "X wins!"
               (sut/get-winner-printable {:board ["X" "X" "X" 3 4 5 6 7 8]})))

    (it "returns O wins!"
      (should= "O wins!"
               (sut/get-winner-printable {:board ["O" "O" "O" 3 4 5 6 7 8]})))

    (it "returns Draw"
      (should= "Draw" (sut/get-winner-printable {:board ["O" "O" "X"
                                                  "X" "X" "O"
                                                  "O" "X" "X"]})))
    )

  (context "get-game-over-printable"
    (it "prints the correct message"
      (should=  ["X wins!"
                 ""
                 "Play Again?"
                 "1. Yes"
                 "2. No"]
                (sut/get-game-over-printable {:board ["X" "X" "X" 3 4 5 6 7 8]})))
    )
  )