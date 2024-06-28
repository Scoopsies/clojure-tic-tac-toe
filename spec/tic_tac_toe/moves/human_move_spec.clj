(ns tic-tac-toe.moves.human-move-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.human-move :as sut]))

(describe "get-player-move"
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

    (it "tells player to enter a valid move if move isn't available"
      (should= "3 is not a valid move\nPlease enter a valid move.\n"
               (->>
                 (sut/get-human-move [0 1 "X" 3])
                 (with-in-str "3\n4")
                 (with-out-str))))
    )

  (context "update-board-human"
    (it "updates the board with appropriate input."
      (with-redefs [println (stub :println)]
        (should= ["X" 1 2 3 4 5 6 7 8]
                 (with-in-str
                   "1"
                   (sut/update-board-human (range 9)))))))
  )