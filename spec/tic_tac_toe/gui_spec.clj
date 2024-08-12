(ns tic-tac-toe.gui-spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.gui :as sut]
            [tic-tac-toe.spec-helper :as helper]))

(describe "gui"
  (with-stubs)
  (redefs-around [q/defsketch (stub :defsketch)])

  (context "draw-square"
    (redefs-around [q/rect (stub :rect)
                    q/fill (stub :fill)])

    (it "invokes rect function"
      (sut/draw-square 0 0)
      (should-have-invoked :rect))

    (it "invokes fill function"
      (sut/draw-square 0 0)
      (should-have-invoked :fill))
    )

  (context "draw-board"
    (it "draws a 3x3 board"
      (with-redefs [sut/draw-square (stub :draw-square)]
        (sut/draw-board 3)
        (should-have-invoked :draw-square {:times 9})))

    (it "draws a 4x4 board"
      (with-redefs [sut/draw-square (stub :draw-square)]
        (sut/draw-board 4)
        (should-have-invoked :draw-square {:times 16})))
    )

  (context "draw-letter"
    (redefs-around [q/fill (stub :fill)
                    q/text (stub :text)
                    q/text-size (stub :text-size)])

    (it "draws an X"
      (sut/draw-letter "X" [0 0] 3)
      (should-have-invoked :text {:with ["X" 0 500/3]}))

    (it "draws an O"
      (sut/draw-letter "O" [0 0] 3)
      (should-have-invoked :text {:with ["O" 0 500/3]}))
    )

  (context "draw-placements"
    (redefs-around [sut/draw-letter (stub :draw-letter)])

    (it "doesn't draw anything on an empty board"
      (sut/draw-placements (range 9))
      (should-not-have-invoked :draw-letter))

    (it "draws an X at position [0 0] on a 3x3"
      (sut/draw-placements (helper/populate-board "X" [0] (range 9)))
      (should-have-invoked :draw-letter {:with ["X" [0 0] 3]}))

    (it "draws an 0 at position [1 1] on a 3x3"
      (sut/draw-placements (helper/populate-board "O" [4] (range 9)))
      (should-have-invoked :draw-letter {:with ["O" [1 1] 3]}))

    (it "draws an 0 at position [3 0] on a 4x4"
      (sut/draw-placements (helper/populate-board "O" [3] (range 16)))
      (should-have-invoked :draw-letter {:with ["O" [3 0] 4]}))
    )
  )
