(ns tic-tac-toe.data.psql
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]))

(def psql-config
  {:dbtype "postgresql"
   :dbname "ttt-test"
   :host "localhost"})

(def psql-db (jdbc/get-datasource psql-config))

(defn drop-table []
  (jdbc/execute!
    psql-db
    ["DROP TABLE IF EXISTS games;"]

    {:return-keys true
     :builder-fn  rs/as-unqualified-maps}))

(defn create-table []
  (jdbc/execute!
    psql-db
    ["CREATE TABLE IF NOT EXISTS games (
     id SERIAL PRIMARY KEY,
     \"game_over?\" BOOLEAN,
     board_size TEXT,
     printables TEXT[],
     ui TEXT,
     move_order INT[],
     o TEXT,
     x TEXT,
     board TEXT[]);"]

    {:return-keys true
     :builder-fn  rs/as-unqualified-maps}))

(defn keyword->TEXT [keyword]
  (subs (str keyword) 1))

(defn clj->sql [state]
  (let [{:keys [ui board-size printables move-order board]} state]
    (assoc state
      :ui         (keyword->TEXT ui)
      :board-size (keyword->TEXT board-size)
      "O"         (keyword->TEXT (state "O"))
      "X"         (keyword->TEXT (state "X"))
      :printables (into-array printables)
      :move-order (into-array move-order)
      :board      (into-array (map str board)))))

(defn add-to-table [state]
  (let [state (clj->sql state)]
    (jdbc/execute! psql-db
                   ["INSERT INTO games (\"game_over?\", board_size, printables, ui, o, move_order, x, board)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                    (:game-over? state)
                    (:board-size state)
                    (:printables state)
                    (:ui state)
                    (state "O")
                    (:move-order state)
                    (state "X")
                    (:board state)])))

(defn- array->vec [map-key sql-data]
  (into [] (.getArray (map-key sql-data))))

(defn strArr->board [strArr]
  (mapv #(if (re-matches #"\d+" %) (Integer/parseInt %) %) strArr))

(defn sql->board [map-key sql-data]
  (strArr->board (array->vec map-key sql-data)))

(defn sql->clj [sql-data]
  {:move-order (array->vec :move_order sql-data)
   :printables (array->vec :printables sql-data)
   :board      (sql->board :board sql-data)
   :board-size (keyword (:board_size sql-data))
   "O"         (keyword (:o sql-data))
   "X"         (keyword (:x sql-data))
   :ui         (keyword (:ui sql-data))
   :game-over? (:game_over? sql-data)
   :id         (:id sql-data)})

(defn retrieve-info []
  (vec
    (jdbc/execute! psql-db
                   ["SELECT * FROM games;"]
                   {:return-keys true
                    :builder-fn  rs/as-unqualified-maps})))