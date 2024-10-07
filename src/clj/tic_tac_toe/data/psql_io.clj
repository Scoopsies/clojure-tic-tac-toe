(ns tic-tac-toe.data.psql-io
  (:require [tic-tac-toe.data.psql :as psql]
            [tic-tac-toe.data.data-io :as data-io]))

(deftype PsqlIO []
  data-io/DataIO

  (read-data [_]
    (map psql/sql->clj (psql/retrieve-info)))

  (write-data [_ content]
    (do
      (psql/drop-table)
      (run! psql/add-to-table! content)))

  (get-last [_]
    (psql/sql->clj (psql/get-last-game)))

  (get-new-id [this]
    (let [last (data-io/get-last this) {:keys [id]} last]
      (if last (inc id) 1)))

  (add-data [_ content]
    (do
      (psql/maybe-create-table!)
      (psql/add-to-table! content)))

  (update-last [_ content]
    (psql/update-row! content))
  )

(defmethod data-io/->data-io :psql []
  (->PsqlIO))