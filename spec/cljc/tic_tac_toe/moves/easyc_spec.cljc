(ns tic-tac-toe.moves.easyc-spec
  (:require [speclj.core #?(:clj :refer :cljs :refer-macros) [should-have-invoked describe it stub with-stubs]]
            [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as moves-core]))

(describe "moves-easyc"
  (with-stubs)
  (it "invokes board/get-random-available"
    (with-redefs [board/get-random-available (stub :get-random)]
      (moves-core/pick-move {"X" :easy :board (range 9)})
      (should-have-invoked :get-random)))
  )