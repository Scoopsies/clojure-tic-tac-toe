(ns tic-tac-toe.corec-spec
  (:require [speclj.core :refer [describe context it should=]]
            [tic-tac-toe.corec :as sut]))

(describe "Corec"

  (context "switch-player"
    (it "switches X to O"
      (should= "O" (sut/switch-player "X")))

    (it "switches O to X"
      (should= "X" (sut/switch-player "O")))
    )
  )