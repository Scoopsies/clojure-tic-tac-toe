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

  (context "get-starter-state"
    (it "returns tui if --tui flag"
      (should= {:ui :tui} (sut/get-starter-state ["--tui"])))

    (it "returns gui if --gui flag"
      (should= {:ui :gui} (sut/get-starter-state ["--gui"])))

    (it "returns replay true and id of 1"
      (should= {:ui :gui :replay? true :id 1} (sut/get-starter-state ["--gui" "--game" "1"])))

    (it "returns replay true and id of 2"
      (should= {:ui :gui :replay? true :id 2} (sut/get-starter-state ["--gui" "--game" "2"])))

    (it "throws an error if first flag isn't --tui or -gui"
      (should-throw (sut/get-starter-state ["--game"])))

    (it "throws an error if second flag isn't --game"
      (should-throw (sut/get-starter-state ["--tui" "--gambit" "1"])))

    (it "throws an error if third argument isn't a number"
      (should-throw (sut/get-starter-state ["--tui" "--gambit" "four"])))

    (it "throws an error if third argument is left off"
      (should-throw (sut/get-starter-state ["--tui" "--game"])))
    )

  (context "-main"
    (it "call starts gui with -gui tag"
      (with-redefs [game/start-game (stub :start-game)]
        (sut/-main "--gui")
        (should-have-invoked :start-game {:with [{:ui :gui}]})))

    (it "call starts gui with -tui tag"
      (with-redefs [game/start-game (stub :start-game)]
        (sut/-main "--tui")
        (should-have-invoked :start-game {:with [{:ui :tui}]})))

    (it "prints title if it had -gui tag"
      (with-redefs [game/start-game (stub :start-game)
                    println (stub :println)]
        (sut/-main "--tui")
        (should-have-invoked :println {:with [sut/title]})))
    )
  )