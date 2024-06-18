(ns tic-tac-toe.play-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.play-game :as sut]))

(describe "Play Game"
  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= "\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n" (with-out-str (sut/print-board (range 0 9))))
      (should= "\n  1  |  2  |  3\n  4  |  X  |  6\n  7  |  8  |  9\n" (with-out-str (sut/print-board [0 1 2 3 "X" 5 6 7 8])))
      (should= "\n  1  |  2  |  O\n  4  |  X  |  6\n  X  |  8  |  O\n" (with-out-str (sut/print-board [0 1 "O" 3 "X" 5 "X" 7 "O"]))))

    (context "get-player-name"
      (it "returns user-input for name"
        (should= "Scoops" (with-in-str "Scoops" (sut/get-player-name)))))

    (context "print-end-game"
      (it "prints an accurate message if game is won (haha never)."
        (should= "\n  X  |  X  |  X\n  4  |  5  |  6\n  7  |  8  |  9\n\nGood Job Scoops, You win!\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["X" "X" "X" 3 4 5 6 7 8]))))

      (it "prints an accurate message if game is lost."
        (should= "\n  O  |  O  |  O\n  4  |  5  |  6\n  7  |  8  |  9\n\nBetter luck next-time Scoops. You lose.\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["O" "O" "O" 3 4 5 6 7 8]))))

      (it "prints an accurate message if game is lost."
        (should= "\n  X  |  O  |  X\n  O  |  O  |  X\n  X  |  X  |  O\n\nYou almost had it Scoops. Draw.\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))))
      )
    )
  )
