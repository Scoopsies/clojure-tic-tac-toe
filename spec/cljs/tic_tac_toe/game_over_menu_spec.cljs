(ns tic-tac-toe.game-over-menu-spec
  (:require-macros [speclj.core :refer [should= it describe before]]
                   [c3kit.wire.spec-helperc :refer [should-not-select should-select]])

  (:require
    [speclj.core]
    [c3kit.wire.spec-helper :as wire]
    [tic-tac-toe.game-over-menu :as sut]))

(describe "game-over-menu"

  (wire/with-root-dom)

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
    )
  )