(ns tic-tac-toe.data-storage.data-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data-storage.data-io :as sut]))

(describe "data-io"
  (with-stubs)
  (redefs-around [sut/file-source "spec/tic_tac_toe/data_storage/test_edn1.edn"])

  (context "read"
    (it "returns data stored in test_edn1"
      (should= [{:id 1} {:id 2}] (sut/read)))

    (it "returns data stored in test_edn2"
      (with-redefs [sut/file-source "spec/tic_tac_toe/data_storage/test_edn2.edn"]
        (should= {:items {:id 2 :message "Hello World!"}} (sut/read))))
    )

  (context "write"
    (it "writes to the file"
      (sut/write {:message "new item!"})
      (should= {:message "new item!"} (sut/read))
      (sut/write [{:id 1} {:id 2}])
      (should= [{:id 1} {:id 2}] (sut/read)))
    )

  (context "get-last"
    (it "returns the last map in the edn vector"
      (should= {:id 2} (sut/get-last))
      (sut/write [{:id 1} {:id 2} {:id 3}])
      (should= {:id 3} (sut/get-last)))
    )

  (context "get-new-id"
    (it "returns a value increased from the last id"
      (let [last-id (:id (sut/get-last))]
        (should= (inc last-id) (sut/get-new-id))))

    (it "gives an id of 0 if its the first element"
      (sut/write [])
      (should= 0 (sut/get-new-id)))
    )

  (context "add"
    (it "adds content to end of edn vector with correct-id"
      (sut/add {})
      (should= {:id 0} (sut/get-last)))
    )

  (it "resets our test files for future testing"
    (sut/write [{:id 1} {:id 2}])
    (should= [{:id 1} {:id 2}] (sut/read)))
  )

