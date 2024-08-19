(ns tic-tac-toe.data.data-io
  (:require [clojure.edn :as edn]))

(defprotocol DataIO
  (read-data [this])
  (write-data [this content])
  (get-last [this])
  (get-new-id [this])
  (add [this content])
  (update-last [this content]))

(def file-source "src/tic_tac_toe/data/games.edn")


(deftype EdnIO []
  DataIO
  (read-data [_] (edn/read-string (slurp file-source)))

  (write-data [_ content]
    (spit file-source content))

  (get-last [this]
    (last (read-data this)))

  (get-new-id [this]
    (let [last-id (:id (get-last this))]
      (if last-id (inc last-id) 0)))

  (add [this content]
    (let [updated-content (assoc content :id (get-new-id this))]
      (write-data this (conj (read-data this) updated-content))))

  (update-last [this content]
    (write-data this (pop (read-data this)))
    (add this content))
  )