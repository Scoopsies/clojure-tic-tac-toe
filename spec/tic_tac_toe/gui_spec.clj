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
      (sut/draw-square [0 0] :3x3)
      (should-have-invoked :rect))

    (it "invokes fill function"
      (sut/draw-square [0 0] :3x3)
      (should-have-invoked :fill))
    )

  (context "draw-board"
    (it "draws a 3x3 board"
      (with-redefs [sut/draw-square (stub :draw-square)]
        (sut/draw-board {:board-size :3x3})
        (should-have-invoked :draw-square {:times 9})))

    (it "draws a 4x4 board"
      (with-redefs [sut/draw-square (stub :draw-square)]
        (sut/draw-board {:board-size :4x4})
        (should-have-invoked :draw-square {:times 16})))
    )

  (context "draw-letter"
    (redefs-around [q/fill (stub :fill)
                    q/text (stub :text)
                    q/text-size (stub :text-size)])

    (it "draws an X"
      (sut/draw-letter "X" [0 0] :3x3)
      (should-have-invoked :text {:with ["X" 0 500/3]}))

    (it "draws an O"
      (sut/draw-letter "O" [0 0] :3x3)
      (should-have-invoked :text {:with ["O" 0 500/3]}))
    )

  (context "draw-placements"
    (redefs-around [sut/draw-letter (stub :draw-letter)])

    (it "doesn't draw anything on an empty board"
      (sut/draw-placements {:board (range 9) :board-size :3x3})
      (should-not-have-invoked :draw-letter))

    (it "draws an X at position [0 0] on a 3x3"
      (sut/draw-placements {:board (helper/populate-board "X" [0] (range 9)) :board-size :3x3})
      (should-have-invoked :draw-letter {:with ["X" [0 0] :3x3]}))

    (it "draws an 0 at position [1 1] on a 3x3"
      (sut/draw-placements {:board (helper/populate-board "O" [4] (range 9)) :board-size :3x3})
      (should-have-invoked :draw-letter {:with ["O" [1 1] :3x3]}))

    (it "draws an 0 at position [3 0] on a 4x4"
      (sut/draw-placements {:board (helper/populate-board "O" [3] (range 16)) :board-size :4x4})
      (should-have-invoked :draw-letter {:with ["O" [3 0] :4x4]}))
    )

  (context "render-menu-line"
    (redefs-around [q/text (stub :text)])

    (it "renders menu line 0"
      (sut/render-menu-line ["Tic" "Tac" "Toe"] 0)
      (should-have-invoked :text {:with ["Tic" 30 60]}))

    (it "renders menu line 1"
      (sut/render-menu-line ["Tic" "Tac" "Toe"] 1)
      (should-have-invoked :text {:with ["Tac" 30 120]}))

    (it "renders menu line 2"
      (sut/render-menu-line ["Tic" "Tac" "Toe"] 2)
      (should-have-invoked :text {:with ["Toe" 30 180]}))
    )

  (context "render-menu"
    (redefs-around [q/background (stub :background)
                    q/fill (stub :fill)
                    q/text-size (stub :text-size)
                    sut/render-menu-line (stub :render-menu-line)])
    (it "renders all items in a list"
      (sut/render-menu {:printables (range 6)})
      (should-have-invoked :render-menu-line {:times 6}))

    (it "renders a black background"
      (sut/render-menu {:printables (range 6)})
      (should-have-invoked :background {:with [0 0 0]}))

    (it "fills letters as white"
      (sut/render-menu {:printables (range 6)})
      (should-have-invoked :fill {:with [255 255 255]}))

    (it "gives text a size of 50"
      (sut/render-menu {:printables (range 6)})
      (should-have-invoked :text-size {:with [50]}))
    )




  )
