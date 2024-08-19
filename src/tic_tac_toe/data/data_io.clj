(ns tic-tac-toe.data.data-io
  (:require [clojure.edn :as edn]
            [tic-tac-toe.config :as config]))

(defprotocol DataIO
  (read-data [this])
  (write-data [this content])
  (get-last [this])
  (get-new-id [this])
  (add-data [this content])
  (update-last [this content]))

(def file-source "src/tic_tac_toe/data/played_games.edn")

(deftype EdnIO []
  DataIO
  (read-data [_]
    (edn/read-string (slurp file-source)))

  (write-data [_ content]
    (spit file-source content))

  (get-last [this]
    (last (read-data this)))

  (get-new-id [this]
    (let [last-id (:id (get-last this))]
      (if last-id (inc last-id) 0)))

  (add-data [this content]
    (let [updated-content (assoc content :id (get-new-id this))]
      (write-data this (conj (read-data this) updated-content))))

  (update-last [this content]
    (write-data this (pop (read-data this)))
    (add-data this content))
  )

(def memory (atom []))

(deftype MemoryIO []
  DataIO

  (read-data [_]
    @memory)

  (write-data [_ content]
    (reset! memory content))

  (get-last [_]
    (last @memory))

  (get-new-id [this]
    (let [last (get-last this) {:keys [id]} last]
      (if last (inc id) 0)))

  (add-data [this content]
    (swap! memory conj (assoc content :id (get-new-id this))))

  (update-last [this content]
    (let [popped-data (pop (read-data this))]
      (write-data this popped-data)
      (add-data this content)))
  )

(defmulti ->data-io (fn [] config/data-store))

(defmethod ->data-io :memory []
  (->MemoryIO))

(defmethod ->data-io :edn []
  (->EdnIO))

(def io (->data-io))

(defn read
  ([] (read-data (->data-io)))
  ([io] (read-data io)))

(defn write [content]
  (write-data (->data-io) content))

(defn pull-last []
  (get-last (->data-io)))

(defn ->new-id []
  (get-new-id (->data-io)))

(defn add [content]
  (add-data (->data-io) content))

(defn update [content]
  (update-last (->data-io) content))