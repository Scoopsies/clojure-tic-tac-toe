(ns tic-tac-toe.play-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.play-game :as sut]
            [tic-tac-toe.printables :as printables]))

(def one-game-X "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  O  |  8  |  X  \n\nO wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def two-games-X  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  O  |  8  |  X  \n\nO wins!\nWould you like to play again? Y/N\n  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  O  |  8  |  X  \n\nO wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def one-game-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nX wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def two-games-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nX wins!\nWould you like to play again? Y/N\n  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  X  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nX wins!\nWould you like to play again? Y/N\nSee you next time!\n")
(def board-size-menu (printables/get-board-size-menu {:ui :tui}))

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

  (context "state-changer"
    (it "X plays as a human"
      (should= {"X" {:move :human}
                :printables sut/player-o-printables} (sut/next-state {} "1")))

    (it "X plays as an easy computer"
      (should= {"X" {:move :easy}
                :printables sut/player-o-printables} (sut/next-state {} "2")))

    (it "X plays as a medium computer"
      (should= {"X" {:move :medium}
                :printables sut/player-o-printables} (sut/next-state {} "3")))

    (it "X plays as a hard computer"
      (should= {"X" {:move :hard}
                :printables sut/player-o-printables} (sut/next-state {} "4")))

    (it "O plays as a human"
      (should= {"X"         {:move :hard}
                "O"         {:move :human}
                :ui :tui
                :printables board-size-menu} (sut/next-state {"X" {:move :hard} :ui :tui} "1")))

    (it "O plays as an easy computer"
      (should= {"X"         {:move :hard}
                "O"         {:move :easy}
                :ui :tui
                :printables board-size-menu} (sut/next-state {"X" {:move :hard} :ui :tui} "2")))

    (it "O plays as medium computer"
      (should= {"X"         {:move :hard}
                "O"         {:move :medium}
                :ui :tui
                :printables board-size-menu} (sut/next-state {"X" {:move :hard} :ui :tui} "3")))

    (it "O plays as a hard computer"
      (should= {"X"         {:move :hard}
                "O"         {:move :hard}
                :ui :tui
                :printables board-size-menu} (sut/next-state {"X" {:move :hard} :ui :tui} "4")))

    (it "plays on a 3x3 board"
      (should= {"X" {:move :hard}
                "O" {:move :hard}
                :board-size :3x3
                :board (range 9)
                :ui :tui
                :menu? false
                :printables (sut/get-move-printables (range 9))}
               (sut/next-state {"X" {:move :hard} "O" {:move :hard} :ui :tui} "1")))

    (it "plays on a 4x4 board"
      (should= {"X" {:move :hard}
                "O" {:move :hard}
                :board-size :4x4
                :board (range 16)
                :menu? false
                :ui :tui
                :printables (sut/get-move-printables (range 16))}
               (sut/next-state {"X" {:move :hard} "O" {:move :hard} :ui :tui} "2")))

    (it "plays on a 3x3x3 board"
      (should= {"X" {:move :hard}
                "O" {:move :hard}
                :board-size :3x3x3
                :board (range 27)
                :menu? false
                :ui :tui
                :printables (sut/get-move-printables (range 27))}
               (sut/next-state {"X" {:move :hard} "O" {:move :hard} :ui :tui} "3")))

    (it "plays X on square 0"
      (should= {"X" {:move :human}
                "O" {:move :easy}
                :board-size :3x3
                :board ["X" 1 2 3 4 5 6 7 8]
                :printables (sut/get-move-printables ["X" 1 2 3 4 5 6 7 8])}
               (sut/next-state {"X" {:move :human}
                                "O" {:move :easy}
                                :board-size :3x3
                                :board (range 9)} 0)))

    (it "plays O on square 1"
      (should= {"X" {:move :easy}
                "O" {:move :human}
                :board-size :3x3
                :board ["X" "O" 2 3 4 5 6 7 8]
                :printables (sut/get-move-printables ["X" "O" 2 3 4 5 6 7 8])}
               (sut/next-state {"X" {:move :easy}
                                "O" {:move :human}
                                :board-size :3x3
                                :board ["X" 1 2 3 4 5 6 7 8]} 1)))
    )

  #_(context "loop-game-play"
    (with-stubs)
    (redefs-around [printables/print-formatted (stub :print-formatted)])
    (it "prints get-move-fn for player X"
      (sut/loop-game-play {:ui :tui :printables sut/player-x-printables})
      (should-have-invoked :print-formatted {:with [sut/player-x-printables]}))

    (it "prints get-move-fn for player O"
      (sut/loop-game-play {:ui :tui :printables sut/player-o-printables})
      (should-have-invoked :print-formatted {:with [sut/player-x-printables]}))
    )

  (context "play-game"
    (it "plays one game with human as X"
      (should= one-game-X (->> (sut/start-game {:ui :tui})
                               (with-in-str "1\n4\n1\n1\n2\n9\nNO")
                               (with-out-str))))

    (it "plays two games with human as X"
      (should= two-games-X (->> (sut/start-game {:ui :tui})
                                (with-in-str "1\n4\n1\n1\n2\n9\nYES\n1\n4\n1\n1\n2\n9\nNO")
                                (with-out-str))))

    (it "plays one game with human as O"
      (should= one-game-O (->> (sut/start-game {:ui :tui})
                       (with-in-str "4\n1\n1\n3\n7\n2\nN")
                       (with-out-str))))

    (it "plays two games with human as O"
      (should= two-games-O (->> (sut/start-game {:ui :tui})
                               (with-in-str "4\n1\n1\n3\n7\n2\nYES\n4\n1\n1\n3\n7\n2\nN")
                               (with-out-str))))
    )
  )

