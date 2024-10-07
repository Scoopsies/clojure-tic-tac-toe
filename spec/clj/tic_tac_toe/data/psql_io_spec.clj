(ns tic-tac-toe.data.psql-io-spec
  (:require [next.jdbc :as jdbc]
            [speclj.core :refer :all]
            [tic-tac-toe.data.data-io :as data-io]
            [tic-tac-toe.data.psql :as psql]
            [tic-tac-toe.data.data-io-spec :as data-spec]))

(def psql-test-config
  {:dbtype "postgresql"
   :dbname "ttt-test"
   :host "localhost"})

(def psql-test-db (jdbc/get-datasource psql-test-config))

(describe "PsqlIO"
  (redefs-around [data-io/data-store (atom :psql) psql/psql-db psql-test-db])
  (before (data-io/write-db data-spec/default-data))
  (data-spec/data-store-specs))