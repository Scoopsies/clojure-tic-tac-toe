(ns tic-tac-toe.data.data-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.data-io :as sut]))

(def io (sut/->EdnIO))

(describe "data-io"
  (with-stubs)
  (redefs-around [sut/file-source "spec/tic_tac_toe/data/test_edn1.edn"])

  (before
    (with-redefs [sut/file-source "spec/tic_tac_toe/data/test_edn1.edn"]
      (sut/write-data io [{:id 1} {:id 2}])))

  (after
    (with-redefs [sut/file-source "spec/tic_tac_toe/data/test_edn1.edn"]
      (sut/write-data io [{:id 1} {:id 2}])))

  (context "read"
    (it "returns data stored in test_edn1"
      (should= [{:id 1} {:id 2}] (sut/read-data io))))

  (context "write"
    (it "writes to the file"
      (sut/write-data io [{:id 3}])
      (should= [{:id 3}] (sut/read-data io))

      (sut/write-data io [{:id 5}])
      (should= [{:id 5}] (sut/read-data io))))

  (context "get-last"
    (it "returns the last map in the edn vector"
      (should= {:id 2} (sut/get-last io)))

    (it "returns nil if edn vector empty"
      (sut/write-data io [])
      (should-not (sut/get-last io)))
    )

  (context "get-new-id"
    (it "returns an id incremented by 1 from the last id"
      (should= 3 (sut/get-new-id io)))

    (it "returns 0 if the edn vector is empty"
      (sut/write-data io [])
      (should= 0 (sut/get-new-id io)))
    )

  (context "add"
    (it "adds content to end of edn vector with new id"
      (sut/add io {})
      (should= [{:id 1} {:id 2} {:id 3}] (sut/read-data io)))

    (it "adds to an empty edn vector with id 0"
      (sut/write-data io [])
      (sut/add io {})
      (should= [{:id 0}] (sut/read-data io)))

    (it "retains content when adding"
      (sut/write-data io [])
      (sut/add io {:message "hello world"})
      (should= [{:id 0 :message "hello world"}] (sut/read-data io)))
    )

  (context "update-last"
    (it "replaces last with content and retains id"
      (sut/update-last io {:message "here"})
      (should= [{:id 1} {:id 2 :message "here"}] (sut/read-data io))))


  )

