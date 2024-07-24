(ns tic-tac-toe.moves.easy-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.easy :as sut]
            [tic-tac-toe.spec-helper :as helper]))



(describe "easy"
  (with-stubs)
  (context "update-easy-board"

    (context "3x3"
      (it "returns a random valid move on empty board"
        (let [board (range 9)]
          (with-redefs [rand-nth (stub :rand-nth {:return 0})]
            (should= (helper/populate-board "X" [0] board) (sut/update-board-easy board)))

          (with-redefs [rand-nth (stub :rand-nth {:return 6})]
            (should= (helper/populate-board "X" [6] board) (sut/update-board-easy board)))))

      (it "returns a random valid move on O's turn"
        (with-redefs [rand-nth (stub :rand-nth {:return 2})]
          (let [board (helper/populate-board "X" [0] (range 9))]
            (should= (helper/populate-board "O" [2] board) (sut/update-board-easy board))))

        (with-redefs [rand-nth (stub :rand-nth {:return 4})]
          (let [board (helper/populate-board "X" [6] (range 9))]
            (should= (helper/populate-board "O" [4] board) (sut/update-board-easy board)))))
      )

    (context "4x4"
      (it "returns a random valid move on empty board"
        (let [board (range 16)]
          (with-redefs [rand-nth (stub :rand-nth {:return 10})]
            (should= (helper/populate-board "X" [10] board) (sut/update-board-easy board)))

          (with-redefs [rand-nth (stub :rand-nth {:return 2})]
            (should= (helper/populate-board "X" [2] board) (sut/update-board-easy board)))))

      (it "returns a random valid move on O's turn"
        (with-redefs [rand-nth (stub :rand-nth {:return 8})]
          (let [board (helper/populate-board "X" [0] (range 16))]
            (should= (helper/populate-board "O" [8] board) (sut/update-board-easy board))))

        (with-redefs [rand-nth (stub :rand-nth {:return 7})]
          (let [board (helper/populate-board "X" [6] (range 27))]
            (should= (helper/populate-board "O" [7] board) (sut/update-board-easy board)))))
      )

    (context "3x3x3"
      (it "returns a random valid move on empty board"
        (let [board (range 27)]
          (with-redefs [rand-nth (stub :rand-nth {:return 20})]
            (should= (helper/populate-board "X" [20] board) (sut/update-board-easy board)))

          (with-redefs [rand-nth (stub :rand-nth {:return 16})]
            (should= (helper/populate-board "X" [16] board) (sut/update-board-easy board)))))

      (it "returns a random valid move on O's turn"
        (with-redefs [rand-nth (stub :rand-nth {:return 23})]
          (let [board (helper/populate-board "X" [0] (range 27))]
            (should= (helper/populate-board "O" [23] board) (sut/update-board-easy board))))

        (with-redefs [rand-nth (stub :rand-nth {:return 15})]
          (let [board (helper/populate-board "X" [6] (range 27))]
            (should= (helper/populate-board "O" [15] board) (sut/update-board-easy board)))))
      )
    )
  )