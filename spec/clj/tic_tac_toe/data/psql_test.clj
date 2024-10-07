(ns tic-tac-toe.data.psql-test
  (:require [next.jdbc :as jdbc]
            [speclj.core :refer :all]
            [tic-tac-toe.data.data-ioc :as data]
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
                 :printables (pr-str ["X wins!" "" "Play Again?" "1. Yes" "2. No"]),
                 :ui         "gui",
                 "O"         "human",
                 :move-order (pr-str [0 1 3 4 6]),
                 "X"         "human",
                 :board      (pr-str ["X" "O" 2 "X" "O" 5 "X" 7 8])})

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
                 :printables (pr-str ["X wins!" "" "Play Again?" "1. Yes" "2. No"]),
                 :ui         "tui",
                 "O"         "hard",
                 :move-order (pr-str [6 4 1 3 0]),
                 "X"         "easy",
                 :board      (pr-str ["X" "O" 2 "X" "O" 5 "X" 7 "8"])})

(def psql-test-config
  {:dbtype "postgresql"
   :dbname "ttt-test"
   :host "localhost"})

(def psql-test-db (jdbc/get-datasource psql-test-config))

(def ttt-test
  (with-redefs [data/data-store (atom :psql)
                sut/psql-db psql-test-db]
    (first (sut/retrieve-info))))

(describe "psql"
  (context "clj->psql"
    (it "turns :ui value into string"
      (should= (:ui sql-state1) (:ui (sut/clj->sql clj-state1)))
      (should= (:ui sql-state2) (:ui (sut/clj->sql clj-state2))))

    (it "turns :board-size value into string"
      (should= (:board-size sql-state1) (:board-size (sut/clj->sql clj-state1)))
      (should= (:board-size sql-state2) (:board-size (sut/clj->sql clj-state2))))

    (it "turns O value into string"
      (should= (sql-state1 "O") ((sut/clj->sql clj-state1) "O"))
      (should= (sql-state2 "O") ((sut/clj->sql clj-state2) "O")))

    (it "turns X value into string"
      (should= (sql-state1 "X") ((sut/clj->sql clj-state1) "X"))
      (should= (sql-state2 "X") ((sut/clj->sql clj-state2) "X")))

    (it "turns :printables value into edn-string"
      (should= (type (:printables sql-state1)) (type (:printables (sut/clj->sql clj-state1))))
      (should= (type (:printables sql-state2)) (type (:printables (sut/clj->sql clj-state2)))))

    (it "turns :move-order value into end-string"
      (should= (type (:move-order sql-state1)) (type (:move-order (sut/clj->sql clj-state1))))
      (should= (type (:move-order sql-state2)) (type (:move-order (sut/clj->sql clj-state2)))))

    (it "turns :board value into java-array of text"
      (should= (type (:board sql-state1)) (type (:board (sut/clj->sql clj-state1))))
      (should= (type (:board sql-state2)) (type (:board (sut/clj->sql clj-state2)))))

    (it "converts :board-size to :board_size"
      (should= (type (:board sql-state1)) (type (:board (sut/clj->sql clj-state1))))
      (should= (type (:board sql-state2)) (type (:board (sut/clj->sql clj-state2))))))

  (context "strArr->board"
    (it "converts string numbers of a board to integers"
      (should= ["X" "O" 2 "X" "O" 5 "X" 7 8] (sut/strArr->board ["X" "O" "2" "X" "O" "5" "X" "7" "8"]))
      (should= ["X" "O" 2 "X" "O" 5 6 7 8] (sut/strArr->board ["X" "O" "2" "X" "O" "5" "6" "7" "8"])))))

(context "sql->clj"
  (it "formats :move-order to a vector"
    (should= [0 1 3 4 6] (:move-order (sut/sql->clj ttt-test))))

  (it "converts :printables to a vector"
    (should= ["X wins!" "" "Play Again?" "1. Yes" "2. No"] (:printables (sut/sql->clj ttt-test))))

  (it "converts :board-size to a :keyword"
    (should= :3x3 (:board-size (sut/sql->clj ttt-test))))

  (it "converts O to a :keyword"
    (should= :human ((sut/sql->clj ttt-test) "O")))

  (it "converts X to a :keyword"
    (should= :human ((sut/sql->clj ttt-test) "O")))

  (it "converts :ui to a :keyword"
    (should= :gui (:ui (sut/sql->clj ttt-test))))

  (it "returns game-over?"
    (should (:game-over? (sut/sql->clj ttt-test))))

  (it "returns :id"
    (should= 1 (:id (sut/sql->clj ttt-test))))

  (it "returns nil if nil goes in"
    (should-not (sut/sql->clj nil)))
  )