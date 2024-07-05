(ns tic-tac-toe.play-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.play-game :as sut]))

(def one-game-X "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nWhat is the name of player X?\nChoose who will play as  O .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  O  |  8  |  9  \n\nComputer-O wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def two-games-X "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nWhat is the name of player X?\nChoose who will play as  O .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  O  |  8  |  9  \n\nComputer-O wins!\nWould you like to play again? Y/N\n  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nWhat is the name of player X?\nChoose who will play as  O .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  7  |  8  |  9  \n\nIt's Computer-O's turn. Please pick a number 1-9.\n\n  X  |  X  |  O  \n  X  |  O  |  6  \n  O  |  8  |  9  \n\nComputer-O wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def one-game-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nChoose who will play as  O .\n1. Human\n2. Computer\nWhat is the name of player O?\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  X  |  X  |  X  \n\nComputer-X wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def two-games-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nChoose who will play as  O .\n1. Human\n2. Computer\nWhat is the name of player O?\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  X  |  X  |  X  \n\nComputer-X wins!\nWould you like to play again? Y/N\n  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nChoose who will play as  X .\n1. Human\n2. Computer\nChoose your dificulty level.\n1. Hard\n2. Medium\n3. Easy\nChoose who will play as  O .\n1. Human\n2. Computer\nWhat is the name of player O?\nWhat size board would you like to play on? (3 or 4 currently supported)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Scoops's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  7  |  X  |  X  \n\nIt's Computer-X's turn. Please pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  O  |  6  \n  X  |  X  |  X  \n\nComputer-X wins!\nWould you like to play again? Y/N\nSee you next time!\n")

(describe "play-game"
  (with-stubs)

  (context "play-again?"
    (it "returns true if user inputs yes variations"
      (with-redefs [println (stub :println)]
        (should (with-in-str "Yes" (sut/play-again?)))
        (should (with-in-str "YES" (sut/play-again?)))
        (should (with-in-str "yes" (sut/play-again?)))
        (should (with-in-str "y" (sut/play-again?)))
        (should (with-in-str "Y" (sut/play-again?)))))

    (it "returns false if user inputs \"no\" variations"
      (with-redefs [println (stub :println)]
        (should-not (with-in-str "NO" (sut/play-again?)))
        (should-not (with-in-str "No" (sut/play-again?)))
        (should-not (with-in-str "no" (sut/play-again?)))
        (should-not (with-in-str "n" (sut/play-again?)))
        (should-not (with-in-str "N" (sut/play-again?))))
      )

    (it "repeats question until user responds with valid input."
      (should= (apply str (repeat 2 "Would you like to play again? Y/N\n"))
               (with-out-str
                 (with-in-str "lol\nn" (sut/play-again?)))))
    )

  (context "play-game"
    (it "plays one game with human as X"
      (should= one-game-X (->> (sut/play-game)
                               (with-in-str "1\nScoops\n2\n1\n3\n1\n2\n4\nNO")
                               (with-out-str))))

    (it "plays two games with human as X"
      (should= two-games-X (->> (sut/play-game)
                                (with-in-str "1\nScoops\n2\n1\n3\n1\n2\n4\nYES\n1\nScoops\n2\n1\n3\n1\n2\n4\nNO")
                                (with-out-str))))

    (it "plays one game with human as O"
      (should= one-game-O (->> (sut/play-game)
                       (with-in-str "2\n1\n1\nScoops\n3\n5\n3\nNO")
                       (with-out-str))))

    (it "plays two games with human as O"
      (should= two-games-O (->> (sut/play-game)
                               (with-in-str "2\n1\n1\nScoops\n3\n5\n3\nYES\n2\n1\n1\nScoops\n3\n5\n3\nNO")
                               (with-out-str))))
    )
  )

