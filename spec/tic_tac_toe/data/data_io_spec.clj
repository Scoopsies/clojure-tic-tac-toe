(ns tic-tac-toe.data.data-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.config :as config]
            [tic-tac-toe.data.data-io :as sut]))

(def default-data [{:id 1} {:id 2}])

(defn data-store-specs [io]
  (context "data store"
    (context "read-data"
      (it "returns data stored in test_edn1"
        (should= default-data (sut/read io))))

    (context "write-data"
      (it "writes to the file"
        (sut/write [{:id 3}])
        (should= [{:id 3}] (sut/read io))

        (sut/write [{:id 5}])
        (should= [{:id 5}] (sut/read io))))

    (context "get-last"
      (it "returns the last map in the edn vector"
        (should= {:id 2} (sut/pull-last)))

      (it "returns nil if edn vector empty"
        (sut/write [])
        (should-not (sut/pull-last)))
      )

    (context "get-new-id"
      (it "returns an id incremented by 1 from the last id"
        (should= 3 (sut/->new-id)))

      (it "returns 0 if the edn vector is empty"
        (sut/write [])
        (should= 0 (sut/->new-id)))
      )

    (context "add"
      (it "adds content to end of edn vector with new id"
        (sut/add {})
        (should= [{:id 1} {:id 2} {:id 3}] (sut/read)))

      (it "adds to an empty edn vector with id 0"
        (sut/write [])
        (sut/add {})
        (should= [{:id 0}] (sut/read)))

      (it "retains content when adding"
        (sut/write [])
        (sut/add {:message "hello world"})
        (should= [{:id 0 :message "hello world"}] (sut/read)))
      )

    (context "update-last"
      (it "replaces last with content and retains id"
        (sut/update {:message "here"})
        (should= [{:id 1} {:id 2 :message "here"}] (sut/read)))
      ))
  )

(describe "Memory-IO"

  (redefs-around [config/data-store :memory])

  (before (reset! sut/memory default-data))

  (data-store-specs (sut/->MemoryIO))
    )

(describe "EdnIO"
  (redefs-around [sut/file-source "spec/tic_tac_toe/data/test_edn1.edn"
                  config/data-store :edn])

  (before (sut/write default-data))

  (data-store-specs (sut/->EdnIO))
  )
