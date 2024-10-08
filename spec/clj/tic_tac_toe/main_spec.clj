(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.data-ioc :as data]
            [tic-tac-toe.play-gamec :as game]
            [tic-tac-toe.main :as sut]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.state-initializerc :as state]))

(def one-game-X "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def two-games-X  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def one-game-O  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def two-games-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")



(describe "main"
  (with-stubs)

  (before (data/write-db [])
          (reset! data/data-store :memory))

  (context "-main"
    (redefs-around [game/loop-game-play (stub :loop-gp)
                    println (stub :println)])

    (it "should have invoked parse-args with the args"
      (with-redefs [state/parse-args (stub :parse-args)]
        (sut/-main "--gui")
        (should-have-invoked :parse-args {:with [["--gui"]]})))

    (it "should print the title screen if it's a --tui"
        (sut/-main "--tui")
        (should-have-invoked :println {:with [printables/title]}))

    (it "should not print the title screen if it's gets --gui"
        (sut/-main "--gui")
        (should-not-have-invoked :println {:with [printables/title]}))

    (it "should have invoked loop-game-play with parsed state"
      (let [initial-state {:ui :tui, :printables ["Who will play as X?" "1. Human" "2. Computer Easy" "3. Computer Medium" "4. Computer Hard"], :game-over? false}]
        (with-redefs [game/loop-game-play (stub :loop-gp)]
          (sut/-main "--tui")
          (should-have-invoked :loop-gp {:with [initial-state]}))))
    )

  (context "plays-games"
      (it "plays one game with human as X"
        (should= one-game-X (->> (sut/-main "--tui")
                                 (with-in-str "1\n4\n1\n1\n2\n9\n2")
                                 (with-out-str))))

      (it "plays two games with human as X"
        (should= two-games-X (->> (sut/-main "--tui")
                                  (with-in-str "1\n4\n1\n1\n2\n9\n1\n1\n4\n1\n1\n2\n9\n2")
                                  (with-out-str))))

      (it "plays one game with human as O"
        (should= one-game-O (->> (sut/-main "--tui")
                                 (with-in-str "4\n1\n1\n3\n7\n2\n2")
                                 (with-out-str))))

      (it "plays two games with human as O"
        (should= two-games-O (->> (sut/-main "--tui")
                                  (with-in-str "4\n1\n1\n3\n7\n2\n1\n4\n1\n1\n3\n7\n2\n2")
                                  (with-out-str)))))
  )