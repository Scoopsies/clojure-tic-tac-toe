(ns tic-tac-toe.menu-spec
  (:require-macros [speclj.core :refer [should= it focus-describe describe before context stub should-have-invoked]])
  (:require
    [c3kit.wire.spec-helper :as wire]
    [reagent.core :as reagent]
    [speclj.core]
    [tic-tac-toe.menu :as sut]))

(def state (reagent/atom {}))

(describe "menu"

  (wire/with-root-dom)

    (it "shows header for current state"
      (wire/render (sut/get-menu (delay {:printables ["title"]})))
      (should= "title" (wire/text "#-printable-0"))
      )

    (it "shows button for state with one option"
      (wire/render (sut/get-menu (delay {:printables ["title" "option1"]})))
      (should= "option1" (wire/text "#-select-1")))

    (it "shows buttons for state with multiple options"
      (wire/render (sut/get-menu (delay {:printables ["title" "option1" "option2" "option3"]})))
      (should= "option1" (wire/text "#-select-1"))
      (should= "option2" (wire/text "#-select-2"))
      (should= "option3" (wire/text "#-select-3")))
  )