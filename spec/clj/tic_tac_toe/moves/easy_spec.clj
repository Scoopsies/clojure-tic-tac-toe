(ns tic-tac-toe.moves.easy-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.easy :as sut]
            [tic-tac-toe.moves.core :as moves-core]))

(describe "moves-easy"
  (with-stubs)
  (it "invokes board/get-random-available"
    (with-redefs [board/get-random-available (stub :get-random)]
      (moves-core/pick-move {"X" :easy :board (range 9)})
      (should-have-invoked :get-random)))
  )