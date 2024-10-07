(ns tic-tac-toe.data.data-io)

(def data-store (atom :memory))

(defprotocol DataIO
  (read-data [this])
  (write-data [this content])
  (get-last [this])
  (get-new-id [this])
  (add-data [this content])
  (update-last [this content]))

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
      (if last (inc id) 1)))

  (add-data [this content]
    (swap! memory conj (assoc content :id (get-new-id this))))

  (update-last [this content]
    (let [popped-data (pop (read-data this))]
      (write-data this popped-data)
      (add-data this content)))
  )

(defmulti ->data-io (fn [] @data-store))

(defmethod ->data-io :memory []
  (->MemoryIO))

(defn read-db []
  (read-data (->data-io)))

(defn write-db [content]
  (write-data (->data-io) content))

(defn pull-last-db []
  (get-last (->data-io)))

(defn ->new-id []
  (get-new-id (->data-io)))

(defn add-db [content]
  (add-data (->data-io) content))

(defn update-db [content]
  (update-last (->data-io) content))