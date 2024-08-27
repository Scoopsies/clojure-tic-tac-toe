(ns tic-tac-toe.play-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.config :as config]
            [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.play-game :as sut]
            [tic-tac-toe.printables :as printables]))

(def one-game-X "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def two-games-X  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  2  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  3  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  X  |  X  |  O  \n  4  |  O  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\nO wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def one-game-O  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def two-games-O "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗\n     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝\n     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nWho will play as X?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWho will play as O?\n1. Human\n2. Computer Easy\n3. Computer Medium\n4. Computer Hard\n\nWhat size board?\n1. 3x3\n2. 4x4\n3. 3x3x3 (3-D)\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  9  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  3  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  8  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  7  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  5  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  2  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\n  1  |  O  |  O  \n  4  |  X  |  6  \n  O  |  X  |  X  \n\nPlease pick a number 1-9.\n\nX wins!\n\nPlay Again?\n1. Yes\n2. No\n\nSee you next time!\n\n")
(def board-size-menu (printables/get-board-size-menu {:ui :tui}))

(describe "play-game"
  (with-stubs)

  (redefs-around [config/data-store :memory])
  (before (data/write-db []))

  (context "handle-replay"
    (it "returns the state of the last game if selection is 1"
      (should= {:id 1 :ui :tui} (sut/handle-continue {:last-game {:id 1 :ui :tui} :ui :tui} "1")))

    (it "it changes last games :ui to current games :ui"
      (should= {:id 1 :ui :tui} (sut/handle-continue {:last-game {:id 1 :ui :gui} :ui :tui} "1")))

    (it "disassociates last-game and associates player-x printables if selection 2"
      (should= {:printables printables/player-x-printables}
               (sut/handle-continue {:last-game {:id 1}} "2")))

    (it "returns original state if no matching selection"
      (let [state {:last-game {:id 1}}]
        (should= state (sut/handle-continue state "3"))
        (should= state (sut/handle-continue state nil))))
    )

  (context "state-changer"

    (it "invokes handle-replay"
      (with-redefs [sut/handle-continue (stub :replay)]
        (sut/get-next-state {:last-game {:id 1}} "1")
        (should-have-invoked :replay {:with [{:last-game {:id 1}} "1"]})))

    (it "X plays as a human"
      (should= {"X" :human
                :printables printables/player-o-printables} (sut/get-next-state {} "1")))

    (it "X plays as an easy computer"
      (should= {"X" :easy
                :printables printables/player-o-printables} (sut/get-next-state {} "2")))

    (it "X plays as a medium computer"
      (should= {"X" :medium
                :printables printables/player-o-printables} (sut/get-next-state {} "3")))

    (it "X plays as a hard computer"
      (should= {"X" :hard
                :printables printables/player-o-printables} (sut/get-next-state {} "4")))

    (it "O plays as a human"
      (should= {"X" :hard
                "O" :human
                :ui :tui
                :printables board-size-menu} (sut/get-next-state {"X" :hard :ui :tui} "1")))

    (it "O plays as an easy computer"
      (should= {"X" :hard
                "O" :easy
                :ui :tui
                :printables board-size-menu} (sut/get-next-state {"X" :hard :ui :tui} "2")))

    (it "O plays as medium computer"
      (should= {"X" :hard
                "O" :medium
                :ui :tui
                :printables board-size-menu} (sut/get-next-state {"X" :hard :ui :tui} "3")))

    (it "O plays as a hard computer"
      (should= {"X" :hard
                "O" :hard
                :ui :tui
                :printables board-size-menu} (sut/get-next-state {"X" :hard :ui :tui} "4")))

    (it "Chooses a 3x3 board"
      (should= {"X" :hard
                "O" :hard
                :board-size :3x3
                :board (range 9)
                :ui :tui
                :printables (printables/get-move-printables (range 9))}
               (sut/get-next-state {"X" :hard "O" :hard :ui :tui} "1")))

    (it "Chooses a 4x4 board"
      (should= {"X" :hard
                "O" :hard
                :board-size :4x4
                :board (range 16)
                :ui :tui
                :printables (printables/get-move-printables (range 16))}
               (sut/get-next-state {"X" :hard "O" :hard :ui :tui} "2")))

    (it "Chooses a 3x3x3 board"
      (should= {"X" :hard
                "O" :hard
                :board-size :3x3x3
                :board (range 27)
                :ui :tui
                :printables (printables/get-move-printables (range 27))}
               (sut/get-next-state {"X" :hard "O" :hard :ui :tui} "3")))

    (it "plays X on square 0"
      (should= {"X" :human
                "O" :easy
                :id 1
                :board-size :3x3
                :board ["X" 1 2 3 4 5 6 7 8]
                :move-order [0]
                :printables (printables/get-move-printables ["X" 1 2 3 4 5 6 7 8])}
               (sut/get-next-state {
                                    "X" :human
                                    "O" :easy
                                    :board-size :3x3
                                    :board      (range 9)} 0)))

    (it "plays O on square 1"
      (should= {"X" :easy
                "O" :human
                :id 1
                :board-size :3x3
                :board ["X" "O" 2 3 4 5 6 7 8]
                :move-order [0 1]
                :printables (printables/get-move-printables ["X" "O" 2 3 4 5 6 7 8])}
               (sut/get-next-state {
                                    "X" :easy
                                    "O" :human
                                    :board-size :3x3
                                    :move-order [0]
                                    :board ["X" 1 2 3 4 5 6 7 8]} 1)))

    (it "plays X on square 2"
      (should= {"X" :easy
                "O" :human
                :id 1
                :board-size :3x3
                :board ["X" "O" "X" 3 4 5 6 7 8]
                :move-order [0 1 2]
                :printables (printables/get-move-printables ["X" "O" "X" 3 4 5 6 7 8])}
               (sut/get-next-state {
                                    "X" :easy
                                    "O" :human
                                    :board-size :3x3
                                    :move-order [0 1]
                                    :board ["X" "O" 2 3 4 5 6 7 8]} 2)))
    )

  (context "make-move"
    (it "adds data if no data exists before"
      (let [board ["X" 1 2 3 4 5 6 7 8]
            updated-board (board/update-board 1 board)
            printables (printables/get-move-printables updated-board)]
        (sut/make-move {:move-order [0] :board ["X" 1 2 3 4 5 6 7 8]} 1)
        (should= [{:id 1 :move-order [0 1] :board ["X" "O" 2 3 4 5 6 7 8] :printables printables}]
                 (data/read-db))
        )
      )

    (it "adds data if has no id"
      (data/write-db [{:id 0}])
      (let [board ["X" 1 2 3 4 5 6 7 8]
            updated-board (board/update-board 1 board)
            printables (printables/get-move-printables updated-board)]
        (sut/make-move {:move-order [0] :board ["X" 1 2 3 4 5 6 7 8]} 1)
        (should= [{:id 0} {:id 1 :move-order [0 1] :board ["X" "O" 2 3 4 5 6 7 8] :printables printables}]
                 (data/read-db))))

    (it "overwrites last if it does have id"
      (data/write-db [{:id 0} {:id 2}])
      (let [board ["X" 1 2 3 4 5 6 7 8]
            updated-board (board/update-board 1 board)
            printables (printables/get-move-printables updated-board)]
        (sut/make-move {:id 1 :move-order [0] :board ["X" 1 2 3 4 5 6 7 8]} 1)
        (should= [{:id 0} {:id 1 :move-order [0 1] :board ["X" "O" 2 3 4 5 6 7 8] :printables printables} ]
                 (data/read-db))))

    )

  (context "start-game"

    (context "plays-games"
      (it "plays one game with human as X"
        (should= one-game-X (->> (sut/start-game ["--tui"])
                                 (with-in-str "1\n4\n1\n1\n2\n9\n2")
                                 (with-out-str))))

      (it "plays two games with human as X"
        (should= two-games-X (->> (sut/start-game ["--tui"])
                                  (with-in-str "1\n4\n1\n1\n2\n9\n1\n1\n4\n1\n1\n2\n9\n2")
                                  (with-out-str))))

      (it "plays one game with human as O"
        (should= one-game-O (->> (sut/start-game ["--tui"])
                                 (with-in-str "4\n1\n1\n3\n7\n2\n2")
                                 (with-out-str))))

      (it "plays two games with human as O"
        (should= two-games-O (->> (sut/start-game ["--tui"])
                                  (with-in-str "4\n1\n1\n3\n7\n2\n1\n4\n1\n1\n3\n7\n2\n2")
                                  (with-out-str)))))
    )
  )