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

  (context "print-row"
    (it "returns row stated, with index incremented"
      (should= "  1  |  2  |  3\n" (with-out-str (sut/print-row 1 (range 9)))))
    )

  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= "\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n\n"
               (with-out-str
                 (sut/print-board
                   (range 0 9))))
      (should= "\n  1  |  2  |  3\n  4  |  X  |  6\n  7  |  8  |  9\n\n"
               (with-out-str
                 (sut/print-board
                   [0 1 2 3 "X" 5 6 7 8])))
      (should= "\n  1  |  2  |  O\n  4  |  X  |  6\n  X  |  8  |  O\n\n"
               (with-out-str
                 (sut/print-board
                   [0 1 "O" 3 "X" 5 "X" 7 "O"]))))
    )

  (context "print-game-over"
    (it "prints an accurate message if game is won (haha never)."
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
      (should= "Who will be playing as X?\n" (with-out-str
                                               (sut/print-get-player-info "X"))))

    (it "prints the correct info for O"
      (should= "Who will be playing as O?\n" (with-out-str
                                               (sut/print-get-player-info "O"))))
    )
  )