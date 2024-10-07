(ns tic-tac-toe.data.edn-io
  (:require [clojure.edn :as edn]
            [tic-tac-toe.data.data-io :as data-io]))

(def file-source "src/clj/tic_tac_toe/data/played_games.edn")

(deftype EdnIO []
  data-io/DataIO

  (read-data [_]
    (edn/read-string (slurp file-source)))

  (write-data [_ content]
    (spit file-source content))

  (get-last [this]
    (last (data-io/read-data this)))

  (get-new-id [this]
    (let [last-id (:id (data-io/get-last this))]
      (if last-id (inc last-id) 1)))

  (add-data [this content]
    (let [updated-content (assoc content :id (data-io/get-new-id this))]
      (data-io/write-data this (conj (data-io/read-data this) updated-content))))

  (update-last [this content]
    (data-io/write-data this (pop (data-io/read-data this)))
    (data-io/add-data this content))
  )

(defmethod data-io/->data-io :edn []
  (->EdnIO))