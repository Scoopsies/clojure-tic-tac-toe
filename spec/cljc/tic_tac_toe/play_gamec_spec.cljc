(ns tic-tac-toe.play-gamec-spec
  (:require [speclj.core #?(:clj :refer :cljs :refer-macros) [describe with-stubs stub before context it should= should-have-invoked]]
            [tic-tac-toe.boardc :as board]
            [tic-tac-toe.data.data-ioc :as data]
            [tic-tac-toe.play-gamec :as sut]
            [tic-tac-toe.printablesc :as printables]))

(def board-size-menu (printables/get-board-size-menu {:ui :tui}))

(describe "play-gamec"
  (with-stubs)

  (before
    (reset! data/data-store :memory)
    (data/write-db []))

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
  )