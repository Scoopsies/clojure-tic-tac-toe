(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [should= it describe before]]
                   [c3kit.wire.spec-helperc :refer [should-not-select should-select]])
  (:require
    [speclj.core]
    [c3kit.wire.spec-helper :as wire]
    [tic-tac-toe.main :as sut]))

(describe "main"
  (wire/with-root-dom)
  (before
    (wire/render [sut/app]))

  (it "Adds a title at the top of the screen"
    (should-select "#title")
    (should= "Tic-Tac-Toe" (wire/text "#title h1")))

  (it "Displays initial options"
    (should= "Who will play as X?" (wire/text "#-printable-0"))
    (should= "1. Human" (wire/text "#-select-1"))
    (should= "2. Computer Easy" (wire/text "#-select-2"))
    (should= "3. Computer Medium" (wire/text "#-select-3"))
    (should= "4. Computer Hard" (wire/text "#-select-4"))
    )

  #_(it "Asks for player X"
    (should= "Who will play as X?" (wire/text "#-printable-0")))

  #_(it "Selects Human for X"
    (wire/click! "#-select-0")
    (should= "Who will play as O?" (wire/text "#-printable-0"))))