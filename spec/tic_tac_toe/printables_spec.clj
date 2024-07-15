(ns tic-tac-toe.printables-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.printables :as sut]
            [tic-tac-toe.settings-spec :as settings]))

(describe "printables"
  (context "print-valid-move-error"
    (it "returns an error message"
      (should= "Ham Sandwich is not a valid move\nPlease enter a valid move.\n"
               (with-out-str
                 (sut/print-valid-move-error "Ham Sandwich")))))

  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= "\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\n"
               (with-out-str
                 (sut/print-board
                   (range 9))))
      (should= "\n  1  |  2  |  3  \n  4  |  X  |  6  \n  7  |  8  |  9  \n\n"
               (with-out-str
                 (sut/print-board
                   [0 1 2 3 "X" 5 6 7 8])))
      (should= "\n  1  |  2  |  O  \n  4  |  X  |  6  \n  X  |  8  |  O  \n\n"
               (with-out-str
                 (sut/print-board
                   [0 1 "O" 3 "X" 5 "X" 7 "O"]))))

    (it "prints a 4 x 4 board"
      (should= "\n  1  |  2  |  3  |  4  \n  5  |  6  |  7  |  8  \n  9  |  10 |  11 |  12 \n  13 |  14 |  15 |  16 \n\n"
               (with-out-str
                 (sut/print-board (range 16)))))

    (it "prints a 3x3x3 board"
      (should= "  1  |  2  |  3    10 |  11 |  12   19 |  20 |  21 \n  4  |  5  |  6    13 |  14 |  15   22 |  23 |  24 \n  7  |  8  |  9    16 |  17 |  18   25 |  26 |  27 \n"
               (with-out-str (sut/print-board (range 27)))))
    )

  (context "print-game-over"
    (it "prints an accurate message if game is won."
      (should= "Scoops wins!\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["X" "X" "X" 3 4 5 6 7 8]
                   settings/human-computer-settings))))

    (it "prints an accurate message if game is lost."
      (should= "Computer-O wins!\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["O" "O" "O" 3 4 5 6 7 8]
                   settings/human-computer-settings))))

    (it "prints an accurate message if game is draw."
      (should= "Draw.\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   ["X" "O" "X" "O" "O" "X" "X" "X" "O"]
                   settings/human-computer-settings))))
    )

  (context "print-get-player-info"
    (it "prints the correct info for X"
      (should= "Who will be playing as X?\n"
               (with-out-str
                 (sut/print-get-player-info "X"))))

    (it "prints the correct info for O"
      (should= "Who will be playing as O?\n"
               (with-out-str
                 (sut/print-get-player-info "O"))))
    )

  (context "print-get-move-screen"
    (it "displays proper result for 3x3 board"
      (should= "It's Micha's turn. Please pick a number 1-9.\n"
               (with-out-str
                 (sut/print-get-move (range 9) {"X" {:player-name "Micha"}}))))

    (it "displays proper result for 4x4 board"
      (should= "It's Alex's turn. Please pick a number 1-16.\n"
               (with-out-str
                 (sut/print-get-move (range 16) {"X" {:player-name "Alex"}}))))

    (it "displays correct possessive name for names ending with s"
      (should= "It's Scoops' turn. Please pick a number 1-16.\n"
               (with-out-str
                 (sut/print-get-move (range 16) {"X" {:player-name "Scoops"}}))))
    )
  )