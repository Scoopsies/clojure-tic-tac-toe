(ns tic-tac-toe.play-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.play-game :as sut]))

(def two-games "████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n   ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗  \n   ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝  \n   ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n   ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n                                                                               \nWhat is your name?\n\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  2  |  3\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nBetter luck next-time Scoops. You lose.\n\nWould you like to play again? Y/N\n\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  2  |  3\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nBetter luck next-time Scoops. You lose.\n\nWould you like to play again? Y/N\nSee you next time!\n" )
(def one-game "████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n   ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗  \n   ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝  \n   ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n   ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n                                                                               \nWhat is your name?\n\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  2  |  3\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  4  |  O  |  6\n  7  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nIt's your turn Scoops. Please pick a number 1-9.\n\n  X  |  X  |  O\n  X  |  O  |  6\n  O  |  8  |  9\n\nBetter luck next-time Scoops. You lose.\n\nWould you like to play again? Y/N\nSee you next time!\n")

(describe "Play Game"
  (context "print-board"
    (it "prints the board with correct values incremented by 1"
      (should= "\n  1  |  2  |  3\n  4  |  5  |  6\n  7  |  8  |  9\n" (with-out-str (sut/print-board (range 0 9))))
      (should= "\n  1  |  2  |  3\n  4  |  X  |  6\n  7  |  8  |  9\n" (with-out-str (sut/print-board [0 1 2 3 "X" 5 6 7 8])))
      (should= "\n  1  |  2  |  O\n  4  |  X  |  6\n  X  |  8  |  O\n" (with-out-str (sut/print-board [0 1 "O" 3 "X" 5 "X" 7 "O"]))))

    (context "get-player-name"
      (it "returns user-input for name"
        (should= "Scoops" (with-in-str "Scoops" (sut/get-player-name)))))

    (context "print-game-over"
      (it "prints an accurate message if game is won (haha never)."
        (should= "\n  X  |  X  |  X\n  4  |  5  |  6\n  7  |  8  |  9\n\nGood Job Scoops, You win!\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["X" "X" "X" 3 4 5 6 7 8]))))

      (it "prints an accurate message if game is lost."
        (should= "\n  O  |  O  |  O\n  4  |  5  |  6\n  7  |  8  |  9\n\nBetter luck next-time Scoops. You lose.\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["O" "O" "O" 3 4 5 6 7 8]))))

      (it "prints an accurate message if game is draw."
        (should= "\n  X  |  O  |  X\n  O  |  O  |  X\n  X  |  X  |  O\n\nYou almost had it Scoops. Draw.\n" (with-out-str (sut/print-win-lose-draw "Scoops" ["X" "O" "X" "O" "O" "X" "X" "X" "O"]))))
      )

    (context "get-player-move"
      (it "returns a decrease integer of user input number"
        (should= 0 (with-in-str "1" (sut/get-player-move [0 1 2 3])))
        (should= 2 (with-in-str "3" (sut/get-player-move (range 0 9)))))

      (it "tells player to enter a valid move if move isn't available"
        (should= "3 is not a valid move\nPlease enter a valid move.\n"
                 (->>
                      (sut/get-player-move [3])
                      (with-in-str "3\n4")
                      (with-out-str))))
      )

    (context "play-again?"
      (it "returns true if user inputs yes variations"
        (should (with-in-str "Yes" (sut/play-again?)))
        (should (with-in-str "YES" (sut/play-again?)))
        (should (with-in-str "yes" (sut/play-again?)))
        (should (with-in-str "y" (sut/play-again?)))
        (should (with-in-str "Y" (sut/play-again?))))

      (it "returns false if user inputs \"no\" variations"
        (should-not (with-in-str "NO" (sut/play-again?)))
        (should-not (with-in-str "No" (sut/play-again?)))
        (should-not (with-in-str "no" (sut/play-again?)))
        (should-not (with-in-str "n" (sut/play-again?)))
        (should-not (with-in-str "N" (sut/play-again?)))
        )

      (it "repeats question until user responds properly"
        (should= (apply str (repeat 2 "\nWould you like to play again? Y/N\n"))
                 (with-out-str (with-in-str "lol\nn" (sut/play-again?))))
        (should-not (with-in-str "lol\nn" (sut/play-again?)))
        (should (with-in-str "lol\ny" (sut/play-again?))))
      )

    (context "play-game"
      (it "plays one game"
        (should= one-game (->> (sut/play-game)
                         (with-in-str "Scoops\n1\n2\n4\nNO")
                         (with-out-str))))

      (it "plays two games"
        (should= two-games (->> (sut/play-game)
                         (with-in-str "Scoops\n1\n2\n4\ny\n1\n2\n4\nn")
                         (with-out-str))))
      )
    )
  )

