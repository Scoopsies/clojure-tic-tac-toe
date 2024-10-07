(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as sut]))

(describe "Core"

  (context "switch-player"
    (it "switches X to O"
      (should= "O" (sut/switch-player "X")))

    (it "switches O to X"
      (should= "X" (sut/switch-player "O")))
    )
  )

