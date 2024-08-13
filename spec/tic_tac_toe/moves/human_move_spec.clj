(ns tic-tac-toe.moves.human-move-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.human-move :as sut]))

(describe "human-move"
  (with-stubs)
  (context "get-player-move"
    (it "returns a decrease integer of user input number"
      (should= 0 (with-in-str "1" (sut/get-human-move [0 1 2 3])))
      (should= 2 (with-in-str "3" (sut/get-human-move (range 0 9)))))

    (it "not a valid move followed by valid gives decrease integer of valid"
      (with-redefs [println (stub :println)]
        (->>
          (sut/get-human-move (range 0 9))
          (with-in-str "33\n3")
          (should= 2))))
    )

  (context "print-valid-move-error"
    (it "returns an error message"
      (should= "Ham Sandwich is not a valid move\nPlease enter a valid move.\n\n"
               (with-out-str (sut/print-valid-move-error "Ham Sandwich")))))
  )