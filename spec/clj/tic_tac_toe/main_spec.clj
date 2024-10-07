(ns tic-tac-toe.main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.play-game :as game]
            [tic-tac-toe.main :as sut]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.state-initializer :as state]))

(describe "main"
  (with-stubs)

  (redefs-around [data/data-store (atom :memory)])
  (before (data/write-db []))

  (context "-main"
    (redefs-around [data/data-store (atom :memory)
                    println (stub :println)
                    game/loop-game-play (stub :loop-gp)])

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
  )