(ns tic-tac-toe.board-spec
  (:require-macros [speclj.core :refer [should= it focus-describe describe before context stub should-have-invoked with-stubs]]
                   [c3kit.wire.spec-helperc :refer [should-not-select should-select]])
  (:require [c3kit.wire.spec-helper :as wire]
            [tic-tac-toe.board :as sut]
            [speclj.core]
            [tic-tac-toe.moves.corec :as move]
            [tic-tac-toe.corejs :as corejs]))

(describe "board"

  (wire/with-root-dom)
  (with-stubs)

  (context "render-board"
    (it "should render an empty :3x3 board"
      (wire/render (sut/render-board (delay {:board (tic-tac-toe.boardc/create-board :3x3) :board-size :3x3 "X" :human "O" :human})))
      (should-select "#board")
      (should-select ".board3x3")
      (should= (mapv str (range 1 10)) (wire/select-map wire/text ".cell3x3"))
      (should= 9 (count (wire/select-all ".cell3x3"))))

    (it "should render an empty :4x4 board"
      (wire/render (sut/render-board (delay {:board (tic-tac-toe.boardc/create-board :4x4) :board-size :4x4 "X" :human "O" :human})))
      (should-select "#board")
      (should-select ".board4x4")
      (should= (mapv str (range 1 17)) (wire/select-map wire/text ".cell4x4"))
      (should= 16 (count (wire/select-all ".cell4x4"))))

    (it "should render an empty :3x3x3 board"
      (wire/render (sut/render-board (delay {:board (tic-tac-toe.boardc/create-board :3x3x3) :board-size :3x3x3 "X" :human "O" :human})))
      (should-select "#board")
      (should-select ".board3x3x3")
      (should= (mapv str (range 1 28)) (wire/select-map wire/text ".cell3x3x3"))
      (should= 27 (count (wire/select-all ".cell3x3x3"))))

    (it "should render occupied values on a board"
      (wire/render (sut/render-board (delay {:board ["X" 1 "O" 3 "X" "O" 6 7 8] :board-size :3x3 "X" :human "O" :human})))
      (should= ["X" "2" "O" "4" "X" "O" "7" "8" "9"] (wire/select-map wire/text ".cell3x3")))
    )

  (context "take-computer-turn"
    (it "invokes set-timeout for 1000 seconds"
      (with-redefs [sut/set-timeout (stub :setTimeout)
                    corejs/update-state (stub :update-state)
                    move/pick-move (stub :pick-move)]
                   (sut/take-computer-turn (delay {}))
                   (should-have-invoked :setTimeout)))
    )

  (context "handle-click"
    (it "returns nil if it's the computers turn"
      (should= nil (sut/handle-click (delay {"X" :hard "O" :hard :board (range 9)}) 1)))

    (it "returns nil if the index has already been selected"
      (should= nil (sut/handle-click (delay {"X" :human "O" :human :board ["X" 1 2 3 4 5 6 7 8]}) 0)))

    (it "invokes update-state if human turn and valid move index"
      (with-redefs [corejs/update-state (stub :update-state)]
                   (sut/handle-click (delay {"X" :human "O" :human :board (range 9)}) 1)
                   (should-have-invoked :update-state)))
    )
  )
