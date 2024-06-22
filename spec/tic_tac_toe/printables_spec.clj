(ns tic-tac-toe.printables-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.printables :as sut]))

(describe "printables"
  (context "print-valid-move-error"
    (it "returns an error message"
      (should= "Ham Sandwich is not a valid move\nPlease enter a valid move.\n"
               (with-out-str
                 (sut/print-valid-move-error "Ham Sandwich")))))

  (context "print-row"
    (it "returns row stated, with index incremented"
      (should= "  1  |  2  |  3\n" (with-out-str (sut/print-row 1 (range 9)))))
    )

  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= "  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n"
               (with-out-str
                 (sut/print-board
                   (range 0 9))))
      (should= "  1  |  2  |  3\n  4  |  X  |  6\n  7  |  8  |  9\n"
               (with-out-str
                 (sut/print-board
                   [0 1 2 3 "X" 5 6 7 8])))
      (should= "  1  |  2  |  O\n  4  |  X  |  6\n  X  |  8  |  O\n"
               (with-out-str
                 (sut/print-board
                   [0 1 "O" 3 "X" 5 "X" 7 "O"]))))
    )

  (context "print-game-over"
    (it "prints an accurate message if game is won (haha never)."
      (should= "You win! Good Job Scoops!\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   "Scoops"
                   ["X" "X" "X" 3 4 5 6 7 8]
                   "X"))))

    (it "prints an accurate message if game is lost."
      (should= "You lose. Better luck next-time Alex.\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   "Alex"
                   ["O" "O" "O" 3 4 5 6 7 8]
                   "X"))))

    (it "prints an accurate message if game is draw."
      (should= "Draw. You almost had it Micah.\n"
               (with-out-str
                 (sut/print-win-lose-draw
                   "Micah"
                   ["X" "O" "X" "O" "O" "X" "X" "X" "O"]
                   "X"))))
    )
  )