(ns main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.play-game :as game]
            [main :as sut]))

(describe "main"
  (with-stubs)

  (redefs-around [data/data-store (atom :memory) println (stub :println)])
  (before (data/write-db []))

  (context "-main"
    (it "call starts gui with -gui tag"
      (with-redefs [game/start-game (stub :start-game)]
        (sut/-main "--gui")
        (should-have-invoked :start-game {:with [["--gui"]]})))

    (it "call starts gui with -tui tag"
      (with-redefs [game/start-game (stub :start-game)]
        (sut/-main "--tui")
        (should-have-invoked :start-game {:with [["--tui"]]})))
    )
  )