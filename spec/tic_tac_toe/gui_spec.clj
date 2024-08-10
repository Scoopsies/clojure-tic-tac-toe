(ns tic-tac-toe.gui-spec
  (:require [quil.core :as q]
            [speclj.core :refer :all]
            [tic-tac-toe.gui :as sut]))

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
    (it "draws an X"
      (with-redefs [q/text (stub :text)
                    q/text-size (stub :text-size)]
        (sut/draw-letter "X" 0 0)
        (should-have-invoked :text {:with ["X" 0 125]})))

    (it "draws an O"
      (with-redefs [q/text (stub :text)
                    q/text-size (stub :text-size)]
        (sut/draw-letter "O" 0 0)
        (should-have-invoked :text {:with ["O" 0 125]})))
    )
  )
