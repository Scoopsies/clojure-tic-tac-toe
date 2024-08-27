(ns main-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.config :as config]
            [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.play-game :as game]
            [main :as sut]))

(describe "main"
  (with-stubs)

  (redefs-around [config/data-store :memory println (stub :println)])
  (before (data/write-db []))

  (context "print-menu"
    (redefs-around [println (stub :println)])
    (it "invokes println 4 times"
      (sut/print-menu)
      (should-have-invoked :println {:times 4}))

    (it "prints the menu"
      (sut/print-menu)
      (with-stubbed-invocations [["Would you like to play in the terminal or the gui?"]
                                 ["1. Terminal"]
                                 ["2. Gui"]
                                 ["3. Exit"]]))
    )

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