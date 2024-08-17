(ns tic-tac-toe.data-storage.data-io
  (:require [clojure.edn :as edn]))

(def file-source "spec/tic_tac_toe/data_storage/store.edn")

(defn read []
  (edn/read-string (slurp file-source)))

(defn write [content]
  (spit file-source content))

(defn get-last []
  (last (read)))

(defn get-new-id []
  (let [last-id (:id (get-last))]
    (if last-id (inc last-id) 0)))

(defn add [content]
  (write (conj (read) (assoc content :id (get-new-id)))))