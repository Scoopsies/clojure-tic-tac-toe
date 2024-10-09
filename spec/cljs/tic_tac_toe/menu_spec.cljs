(ns tic-tac-toe.menu-spec
  (:require-macros [speclj.core :refer [should= it focus-describe describe before context stub should-have-invoked]]
                   [c3kit.wire.spec-helperc :refer [should-not-select should-select]])
  (:require
    [c3kit.wire.spec-helper :as wire]
    [reagent.core :as reagent]
    [speclj.core]
    [tic-tac-toe.menu :as sut]))

(def state (reagent/atom {}))

(describe "menu"

  (wire/with-root-dom)

  (context "render-menu"

    (it "shows header for current state"
      (wire/render (sut/render-menu (delay {:printables ["title"]})))
      (should= "title" (wire/text "#-printable-0"))
      )

    (it "shows button for state with one option"
      (wire/render (sut/render-menu (delay {:printables ["title" "option1"]})))
      (should= "option1" (wire/text "#-select-1")))

    (it "shows buttons for state with multiple options"
      (wire/render (sut/render-menu (delay {:printables ["title" "option1" "option2" "option3"]})))
      (should= "option1" (wire/text "#-select-1"))
      (should= "option2" (wire/text "#-select-2"))
      (should= "option3" (wire/text "#-select-3")))
    )

    (context "render-game-over-menu"
      (it "renders with O as the winner"
        (wire/render (sut/render-game-over (delay {:printables ["O Wins!" "" "Play Again?" "1. Yes" "2. No"]})))
        (should-select "#game-over")
        (should= "O Wins!" (wire/text "#winner"))
        (should= "Play Again?" (wire/text "#play-again"))
        (should= "1. Yes" (wire/text "#-select-1"))
        (should= "2. No" (wire/text "#-select-2"))
        )

      (it "renders with O as the winner"
        (wire/render (sut/render-game-over (delay {:printables ["O Wins!" "" "Play Again?" "1. Yes" "2. No"]})))
        (should-select "#game-over")
        (should= "O Wins!" (wire/text "#winner"))
        (should= "Play Again?" (wire/text "#play-again"))
        (should= "1. Yes" (wire/text "#-select-1"))
        (should= "2. No" (wire/text "#-select-2"))
        )

      (it "renders with X as the winner"
        (wire/render (sut/render-game-over (delay {:printables ["X Wins!" "" "Play Again?" "1. Yes" "2. No"]})))
        (should-select "#game-over")
        (should= "X Wins!" (wire/text "#winner"))
        (should= "Play Again?" (wire/text "#play-again"))
        (should= "1. Yes" (wire/text "#-select-1"))
        (should= "2. No" (wire/text "#-select-2"))
        ))
  )