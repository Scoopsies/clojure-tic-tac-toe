(ns tic-tac-toe.data.psql-test
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.psql :as sut]))

(def clj-state1 {:game-over? true,
                 :board-size :3x3,
                 :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"],
                 :ui         :gui,
                 :id         1,
                 "O"         :human,
                 :move-order [0 1 3 4 6],
                 "X"         :human,
                 :board      `("X" "O" 2 "X" "O" 5 "X" 7 8)})

(def sql-state1 {:game-over  true,
                 :board-size "3x3",
                 :printables (into-array ["X wins!" "" "Play Again?" "1. Yes" "2. No"]),
                 :ui         "gui",
                 "O"         "human",
                 :move-order (into-array [0 1 3 4 6]),
                 "X"         "human",
                 :board      (into-array ["X" "O" "2" "X" "O" "5" "X" "7" "8"])})

(def clj-state2 {:game-over? false,
                 :board-size :4x4,
                 :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"],
                 :ui         :tui,
                 :id         2,
                 "O"         :hard,
                 :move-order [6 4 1 3 0],
                 "X"         :easy,
                 :board      `("X" "O" 2 "X" "O" 5 "X" 7 8)})

(def sql-state2 {:game-over  false,
                 :board-size "4x4",
                 :printables (into-array ["X wins!" "" "Play Again?" "1. Yes" "2. No"]),
                 :ui         "tui",
                 "O"         "hard",
                 :move-order (into-array [6 4 1 3 0]),
                 "X"         "easy",
                 :board      (into-array ["X" "O" "2" "X" "O" "5" "X" "7" "8"])})

(def ttt-test (first (sut/retrieve-info)))

(describe "psql"
  (context "format->psql"
    (it "turns :ui value into string"
      (should= (:ui sql-state1) (:ui (sut/format->sql clj-state1)))
      (should= (:ui sql-state2) (:ui (sut/format->sql clj-state2))))

    (it "turns :board-size value into string"
      (should= (:board-size sql-state1) (:board-size (sut/format->sql clj-state1)))
      (should= (:board-size sql-state2) (:board-size (sut/format->sql clj-state2))))

    (it "turns O value into string"
      (should= (sql-state1 "O") ((sut/format->sql clj-state1) "O"))
      (should= (sql-state2 "O") ((sut/format->sql clj-state2) "O")))

    (it "turns X value into string"
      (should= (sql-state1 "X") ((sut/format->sql clj-state1) "X"))
      (should= (sql-state2 "X") ((sut/format->sql clj-state2) "X")))

    (it "turns :printables value into java-array"
      (should= (type (:printables sql-state1)) (type (:printables (sut/format->sql clj-state1))))
      (should= (type (:printables sql-state2)) (type (:printables (sut/format->sql clj-state2)))))

    (it "turns :move-order value into java-array"
      (should= (type (:move-order sql-state1)) (type (:move-order (sut/format->sql clj-state1))))
      (should= (type (:move-order sql-state2)) (type (:move-order (sut/format->sql clj-state2)))))

    (it "turns :board value into java-array of text"
      (should= (type (:board sql-state1)) (type (:board (sut/format->sql clj-state1))))
      (should= (type (:board sql-state2)) (type (:board (sut/format->sql clj-state2)))))

    (it "converts :board-size to :board_size"
      (should= (type (:board sql-state1)) (type (:board (sut/format->sql clj-state1))))
      (should= (type (:board sql-state2)) (type (:board (sut/format->sql clj-state2))))))

  (context "strArr->board"
    (it "converts string numbers of a board to integers"
      (should= ["X" "O" 2 "X" "O" 5 "X" 7 8] (sut/strArr->board ["X" "O" "2" "X" "O" "5" "X" "7" "8"]))
      (should= ["X" "O" 2 "X" "O" 5 6 7 8] (sut/strArr->board ["X" "O" "2" "X" "O" "5" "6" "7" "8"])))))

(context "format->clj"

  (it "formats :move-order to a vector"
    (should= [0 1 3 4 6] (:move-order (sut/format->clj ttt-test))))

  (it "converts :printables to a vector"
    (should= ["X wins!" "" "Play Again?" "1. Yes" "2. No"] (:printables (sut/format->clj ttt-test))))

  (it "converts :board back to a vector of numbers and string"
    (should= ["X" "O" 2 "X" "O" 5 "X" 7 8] (:board (sut/format->clj ttt-test))))

  (it "converts :board-size to a :keyword"
    (should= :3x3 (:board-size (sut/format->clj ttt-test))))

  (it "converts O to a :keyword"
    (should= :human ((sut/format->clj ttt-test) "O")))

  (it "converts X to a :keyword"
    (should= :human ((sut/format->clj ttt-test) "O")))

  (it "converts :ui to a :keyword"
    (should= :gui (:ui (sut/format->clj ttt-test))))

  (it "returns game-over?"
    (should (:game-over? (sut/format->clj ttt-test))))

  (it "returns :id"
    (should= 1 (:id (sut/format->clj ttt-test))))
  )