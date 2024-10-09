(ns tic-tac-toe.main-spec
  (:require-macros [speclj.core :refer [should= it describe before]]
                   [c3kit.wire.spec-helperc :refer [should-not-select should-select]])
  (:require
    [speclj.core]
    [c3kit.wire.spec-helper :as wire]
    [tic-tac-toe.main :as sut]))

(defn should-menu [player-token]
  (should-select "#menu")
  (should= (str "Who will play as " player-token "?") (wire/text "#-printable-0"))
  (should= "1. Human" (wire/text "#-select-1"))
  (should= "2. Computer Easy" (wire/text "#-select-2"))
  (should= "3. Computer Medium" (wire/text "#-select-3"))
  (should= "4. Computer Hard" (wire/text "#-select-4")))

(describe "main"
  (wire/with-root-dom)
  (before
    (wire/render [sut/app]))

  (it "Gets the initial state"
    (should= {:ui :tui, :printables ["Who will play as X?" "1. Human" "2. Computer Easy" "3. Computer Medium" "4. Computer Hard"], :game-over? false}
             @sut/state))

  (it "Adds a title at the top of the screen"
    (should-select "title")
    (should= "Tic-Tac-Toe" (wire/text "#title h1")))

  (it "Displays player x menu options"
    (should-menu "X"))

  (it "Displays player o menu options"
    (wire/click! "#-select-1")
    (should-menu "O"))

  (it "Displays board size menu options"
    (wire/click! "#-select-1")
    (should= "What size board?" (wire/text "#-printable-0"))
    (should= "1. 3x3" (wire/text "#-select-1"))
    (should= "2. 4x4" (wire/text "#-select-2"))
    (should= "3. 3x3x3 (3-D)" (wire/text "#-select-3")))
   )