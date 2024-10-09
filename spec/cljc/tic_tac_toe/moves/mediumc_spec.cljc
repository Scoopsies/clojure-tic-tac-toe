(ns tic-tac-toe.moves.mediumc-spec
  (:require [speclj.core #?(:clj :refer :cljs :refer-macros) [describe it context with-stubs redefs-around stub should= ]]
            [tic-tac-toe.moves.mediumc :as sut]
            [tic-tac-toe.spec-helperc :as helper]))

(def state3x3 {:board-size :3x3})
(def state3x3x3 {:board-size :3x3x3})

(describe "medium -move"
  (context "get-medium-move"
    (context "3x3"
      (with-stubs)
      (redefs-around [rand-nth (stub :rand-nth {:invoke first})])
      (it "returns index of winning move"
        (should= 2 (sut/pick-medium-move (assoc state3x3 :board ["X" "X" 2 "O" "O" 5 6 7 8])))
        (should= 5 (sut/pick-medium-move (assoc state3x3 :board ["X" "X" 2 "O" "O" 5 "X" 7 8]))))

      (it "returns index of blocking move if winning move isn't available"
        (should= 2 (sut/pick-medium-move (assoc state3x3 :board ["X" "X" 2 "O" 4 5 "O" 7 "X"])))
        (should= 5 (sut/pick-medium-move (assoc state3x3 :board [0 "X" 2 "O" "O" 5 6 "X" 8]))))

      (it "returns random available move if can't win or block"
        (should= 0 (sut/pick-medium-move (assoc state3x3 :board (range 9))))
        (should= 1 (sut/pick-medium-move (assoc state3x3 :board ["X" 1 2 3 4 5 6 7 8]))))
      )


    (context "3x3x3"
      (with-stubs)
      (redefs-around [rand-nth (stub :rand-nth {:invoke first})])
      (it "takes center if available"
        (should= 13 (sut/pick-medium-move (assoc state3x3x3 :board (range 27)))))

      (it "takes block if presented"
        (let [board (helper/populate-board "X" [0 1] (range 27))]
          (should= 2 (sut/pick-medium-move (assoc state3x3x3 :board board))))

        (let [board (helper/populate-board "X" [24 25] (range 27))]
          (should= 26 (sut/pick-medium-move (assoc state3x3x3 :board board)))))

      (it "randomizes move if center is taken and block isn't possible"
        (let [board (helper/populate-board "X" [13] (range 27))]
          (should= 0 (sut/pick-medium-move (assoc state3x3x3 :board board)))))
      )
    )
  )
