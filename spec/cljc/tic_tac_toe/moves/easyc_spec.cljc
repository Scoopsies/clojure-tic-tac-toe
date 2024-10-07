(ns tic-tac-toe.moves.easyc-spec
  (:require [speclj.core :refer [describe with-stubs it stub should-have-invoked]]
            [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as moves-core]))

(describe "moves-easyc"
  (with-stubs)
  (it "invokes board/get-random-available"
    (with-redefs [board/get-random-available (stub :get-random)]
      (moves-core/pick-move {"X" :easy :board (range 9)})
      (should-have-invoked :get-random)))
  )