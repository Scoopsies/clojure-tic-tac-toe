(ns tic-tac-toe.moves.corec-spec
  (:require [speclj.core :refer [should= describe context it stub with-stubs redefs-around]]
            [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as sut]
            [tic-tac-toe.spec-helperc :as helper]))

(defn map-add [n coll]
  (map #(+ (* 9 n) %) coll))

(defn take-win-plane? [n]
  (let [board (range 27)
        test (fn [result played-spots]
               (doseq [x played-spots]
                 (should= result (sut/take-win (helper/populate-board "X" x board)))))]

    (test (+ (* 9 n) 0) (map #(map-add n %) [[1 2] [4 8] [3 6]]))
    (test (+ (* 9 n) 1) (map #(map-add n %) [[0 2] [4 7]]))
    (test (+ (* 9 n) 2) (map #(map-add n %) [[0 1] [4 6] [5 8]]))
    (test (+ (* 9 n) 3) (map #(map-add n %) [[0 6] [4 5]]))
    (test (+ (* 9 n) 4) (map #(map-add n %) [[0 8] [1 7] [2 6] [3 5]]))
    (test (+ (* 9 n) 5) (map #(map-add n %) [[2 8] [3 4]]))
    (test (+ (* 9 n) 6) (map #(map-add n %) [[0 3] [2 4] [7 8]]))
    (test (+ (* 9 n) 7) (map #(map-add n %) [[1 4] [6 8]]))
    (test (+ (* 9 n) 8) (map #(map-add n %) [[0 4] [2 5] [6 7]]))))

(describe "moves-core"
  (context "take-block"
    (it "tests"
      (should= 2 (sut/take-block ["X" "X" 2 "O" 4 5 "O" 7 "X"]))))

  (context "take-block"
    (it "returns index of blocking move"
      (should= 2 (sut/take-block ["O" "O" 2 "X" "X" 5 6 7 8]))))

  (context "take-win"
    (with-stubs)
    (redefs-around [board/get-active-player (stub :active-player {:return "X"})])
    (context "3x3"
      (it "takes any presented win"
        (let [board (range 9)
              test (fn [result played-spots]
                     (doseq [x played-spots]
                       (should= result (sut/take-win (helper/populate-board "X" x board)))))]

          (test 0 [[1 2] [4 8] [3 6]])
          (test 1 [[0 2] [4 7]])
          (test 2 [[0 1] [4 6] [5 8]])

          (test 3 [[0 6] [4 5]])
          (test 4 [[0 8] [1 7] [2 6] [3 5]])
          (test 5 [[2 8] [3 4]])

          (test 6 [[0 3] [2 4] [7 8]])
          (test 7 [[1 4] [6 8]])
          (test 8 [[0 4] [2 5] [6 7]]))
        )
      )

    (context "4x4"
      (it "takes any presented win"
        (let [board (range 16)
              test (fn [result played-spots]
                     (doseq [x played-spots]
                       (should= result (sut/take-win (helper/populate-board "X" x board)))))]

          (test 0 [[1 2 3] [4 8 12] [5 10 15]])
          (test 1 [[0 2 3] [5 9 13]])
          (test 2 [[0 1 3] [6 10 14]])
          (test 3 [[0 1 2] [6 9 12] [7 11 15]])
          (test 4 [[0 8 12] [5 6 7]])
          (test 5 [[1 9 13] [4 6 7]])
          (test 6 [[2 10 14] [4 5 7]])
          (test 7 [[3 11 15] [4 5 6]])
          (test 8 [[0 4 12] [9 10 11]])
          (test 9 [[1 5 13] [8 10 11]])
          (test 10 [[2 6 14] [8 9 11]])
          (test 11 [[3 7 15] [8 9 10]])
          (test 12 [[0 4 8] [3 6 9] [13 14 15]])
          (test 13 [[1 5 9] [12 14 15]])
          (test 14 [[2 6 10] [12 13 15]])
          (test 15 [[0 5 10] [3 7 11] [12 13 14]])))
      )

    (context "3x3x3"
      (context "facing forwards"
        (it "takes any presented win on plane 0"
          (take-win-plane? 0))

        (it "takes any presented win on plane 1"
          (take-win-plane? 1))

        (it "takes any presented win on plane 2"
          (take-win-plane? 2)))

      (context "facing sideways"
        (it "takes a row win from side view of cube"
          (should= 21 (sut/take-win (helper/populate-board "X" [3 12] (range 27)))))

        (it "takes a diagonal win from side view of cube"
          (should= 25 (sut/take-win (helper/populate-board "X" [1 13] (range 27)))))
        )

      (context "facing top"
        (it "takes a diagonal win from top view of cube"
          (should= 20 (sut/take-win (helper/populate-board "X" [0 10] (range 27)))))

        (it "takes across cube diagonal win from top view of cube"
          (should= 20 (sut/take-win (helper/populate-board "X" [6 13] (range 27)))))
        )
      )
    )

  (context "get-move-params"
    (it "returns params for X on x's turn"
      (should= :human (sut/get-move-param {:board (range 9)
                                           :board-size :3x3
                                           "X" :human})))

    (it "returns params for O on o's turn"
      (should= :hard (sut/get-move-param {:board (helper/populate-board "X" [0] (range 9))
                                          :board-size :4x4
                                          "O" :hard})))
    )
  )
