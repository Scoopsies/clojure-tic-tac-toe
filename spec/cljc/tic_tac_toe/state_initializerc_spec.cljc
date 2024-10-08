(ns tic-tac-toe.state-initializerc-spec
  (:require [speclj.core :refer [describe with-stubs before context it should= should-not should should-have-invoked should-throw stub redefs-around after]]
            [tic-tac-toe.data.data-ioc :as data]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.state-initializerc :as sut]))

(def game0 {:game-over? true, :board-size :3x3, :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"], :end-game? false, :ui :gui, :id 0, "O" {:move :human}, :menu? true, :move-order [0 1 3 4 6], "X" {:move :human}, :board ["X" "O" 2 "X" "O" 5 "X" 7 8]})
(def game1 {:game-over? true, :board-size :3x3, :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"], :end-game? false, :ui :gui, :id 1, "O" {:move :easy}, :menu? true, :move-order [0 5 4 2 8 1], "X" {:move :human}, :board ["X" "O" "O" 3 "X" "O" 6 7 "X"]})
(def game2 {:game-over? true, :board-size :3x3, :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"], :end-game? false, :ui :gui, :id 2, "O" {:move :human}, :menu? true, :move-order [0 4 1 3 2], "X" {:move :human}, :board ["X" "X" "X" "O" "O" 5 6 7 8]})
(def default-data [game0 game1 game2])

(describe "state initializer"
  (with-stubs)

  (before (data/write-db default-data))

  (context "retrieve-game"
    (it "finds the game with the matching id of 0"
      (should= game0 (sut/retrieve-game 0)))

    (it "finds the game with the matching id of 1"
      (should= game1 (sut/retrieve-game 1)))
    )

  (context "initialize-data"
    (it "scrubs non-essential data and generates new board for game 0"
      (should= {:board-size :3x3
                :ui :gui
                :move-order []
                :board (range 9)
                :printables (printables/get-move-printables (range 9))
                "X" {:move :replay}
                "O" {:move :replay}
                :game-over? false
                :replay-moves [0 1 3 4 6]}
               (sut/initialize-replay 0)))

    (it "scrubs non-essential data and generates new board for game 1"
      (should= {:board-size :3x3
                :ui :gui
                :move-order []
                :board (range 9)
                :printables (printables/get-move-printables (range 9))
                "X" {:move :replay}
                "O" {:move :replay}
                :game-over? false
                :replay-moves [0 5 4 2 8 1]}
               (sut/initialize-replay 1)))
    )

  (context "->initial-state"
    (it "invokes initialize-data with id if :replay? true"
      (with-redefs [sut/initialize-replay (stub :initialize)]
        (sut/->initial-state {:replay? true :id 1})
        (should-have-invoked :initialize {:with [1]})))

    (it "invokes handle-last-game if no :replay?"
      (with-redefs [data/pull-last-db (stub :pull)]
        (sut/->initial-state {})
        (should-have-invoked :pull)))

    (it "assigns previous game printables if last-game unfinished"
      (let [last-game {:id 1 :board ["X" 1 2 3 4 5 6 7 8] :game-over? false}
            result-state {:ui :tui :printables printables/continue-printables :last-game last-game :game-over? false}]
        (data/write-db [last-game])
        (should= result-state (sut/->initial-state {:ui :tui}))))

    (it "assigns player-x printables if last-game was finished"
      (let [last-game {:id 1 :board ["X" "X" "X" 3 4 5 6 7 8] :game-over? true}
            result-state {:ui :tui :printables printables/player-x-printables :game-over? false}]
        (data/write-db [last-game])
        (should= result-state (sut/->initial-state {:ui :tui}))))

    (it "assigns player-x printables if no last game"
      (let [result-state {:ui :tui :printables printables/player-x-printables :game-over? false}]
        (data/write-db [])
        (should= result-state (sut/->initial-state {:ui :tui}))))
    )

  (context "contains-ui?"
    (it "returns nil if it doesn't contain anything"
      (should-not (sut/contains-ui? [])))

    (it "returns true if it contains --tui"
      (should (sut/contains-ui? ["--tui"])))

    (it "returns true if it contains --gui"
      (should (sut/contains-ui? ["--gui"])))
    )

  (context "parse-args"
    (redefs-around [sut/->initial-state (stub :initial-state)])

    (after (reset! data/data-store :memory))

    (it "returns adds tui if --tui flag"
      (sut/parse-args ["--tui"])
      (should-have-invoked :initial-state {:with [{:ui :tui}]}))

    (it "returns gui if --gui flag"
      (sut/parse-args ["--gui"])
      (should-have-invoked :initial-state {:with [{:ui :gui}]}))

    (it "returns replay true and id of 1"
      (sut/parse-args ["--gui" "--game" "1"])
      (should-have-invoked :initial-state {:with [{:ui :gui :replay? true :id 1}]}))

    (it "returns replay true and id of 2"
      (sut/parse-args ["--gui" "--game" "2"])
      (should-have-invoked :initial-state {:with [{:ui :gui :replay? true :id 2}]}))

    (it "throws an error if argument after game isn't a number"
      (should-throw (sut/parse-args ["--tui" "--game" "four"])))

    (it "throws an error if no number after game"
      (should-throw (sut/parse-args ["--tui" "--game"])))

    (it "updates data-store to :edn if --edndb flag"
      (sut/parse-args ["--edndb" "--tui"])
      (should= :edn @data/data-store))

    (it "updates data-store to :psql if --sqldb flag"
      (sut/parse-args ["--sqldb" "--tui"])
      (should= :psql @data/data-store))

    )
  )