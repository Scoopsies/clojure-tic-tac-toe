(ns tic-tac-toe.data.data-io-spec
  (:require [next.jdbc :as jdbc]
            [speclj.core :refer :all]
            [tic-tac-toe.config :as config]
            [tic-tac-toe.data.data-io :as sut]
            [tic-tac-toe.data.psql :as psql]))

(def clj-state1 {:game-over? true,
                 :board-size :3x3,
                 :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"],
                 :ui         :gui,
                 "O"         :human,
                 :move-order [0 1 3 4 6],
                 "X"         :human,
                 :board      `("X" "O" 2 "X" "O" 5 "X" 7 8)})

(def clj-state2 {:game-over? false,
                 :board-size :4x4,
                 :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"],
                 :ui         :tui,
                 "O"         :hard,
                 :move-order [6 4 1 3 0],
                 "X"         :easy,
                 :board      `("X" "O" 2 "X" "O" 5 "X" 7 8)})

(def clj-state3 {:game-over? false,
                 :board-size :3x3,
                 :printables ["X wins!" "" "Play Again?" "1. Yes" "2. No"],
                 :ui         :gui,
                 "O"         :easy,
                 :move-order [6 4 1 3 0],
                 "X"         :easy,
                 :board      `("X" "O" 2 "X" "O" 5 "X" 7 8)})


(def default-data [(assoc clj-state1 :id 1) (assoc clj-state2 :id 2)])

(def test-edn "spec/tic_tac_toe/data/test_edn1.edn")

(def psql-test-config
  {:dbtype "postgresql"
   :dbname "ttt-test"
   :host "localhost"})

(def psql-test-db (jdbc/get-datasource psql-test-config))

(defn data-store-specs []
  (context "data store"

    (context "read-data"
      (it "returns data stored in the <data>"
        (should= default-data (sut/read-db))))

    (context "write-data"
      (it "writes to the <data>"
        (sut/write-db [(assoc clj-state1 :id 1)])
        (should= [(assoc clj-state1 :id 1)] (sut/read-db))

        (sut/write-db [(assoc clj-state2 :id 1)])
        (should= [(assoc clj-state2 :id 1)] (sut/read-db))))

    (context "get-last"
      (it "returns the last map in the <data>"
        (should= (assoc clj-state2 :id 2) (sut/pull-last-db)))

      (it "returns nil if <data> is empty"
        (sut/write-db [])
        (should-not (sut/pull-last-db)))
      )

    (context "get-new-id"
      (it "returns an id incremented by 1 from the last id"
        (should= 3 (sut/->new-id)))

      (it "returns 1 if the edn vector is empty"
        (sut/write-db [])
        (should= 1 (sut/->new-id)))
      )

    (context "add-db"
      (it "adds content to end of data vector with new id"
        (sut/add-db clj-state3)
        (should= [(assoc clj-state1 :id 1) (assoc clj-state2 :id 2) (assoc clj-state3 :id 3)] (sut/read-db)))

      (it "retains content when adding"
        (sut/write-db [])
        (sut/add-db clj-state1)
        (should= [(assoc clj-state1 :id 1)] (sut/read-db)))
      )

    (context "update-last"
      (it "replaces last with content and retains id"
        (sut/update-db (assoc clj-state2 :id 2))
        (should= [(assoc clj-state1 :id 1) (assoc clj-state2 :id 2)] (sut/read-db))

        (let [updated-state (assoc clj-state2 :id 2 :board-size :3x3)]
          (sut/update-db updated-state)
          (should= [(assoc clj-state1 :id 1) updated-state] (sut/read-db))))
      )
    )
  )

(describe "Memory-IO"
  (redefs-around [config/data-store :memory])
  (before (reset! sut/memory default-data))
  (data-store-specs))

(describe "EdnIO"
  (redefs-around [sut/file-source test-edn config/data-store :edn])
  (before (sut/write-db default-data))
  (data-store-specs))

(describe "PsqlIO"
  (redefs-around [config/data-store :psql psql/psql-db psql-test-db])
  (before (sut/write-db default-data))
  (data-store-specs))