(ns tic-tac-toe.data.edn-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.data.edn-io :as sut]
            [tic-tac-toe.data.data-io :as data-io]
            [tic-tac-toe.data.data-io-spec :as data-spec]))

(def test-edn "spec/clj/tic_tac_toe/data/test_edn1.edn")

(describe "EdnIO"
  (redefs-around [sut/file-source test-edn data-io/data-store (atom :edn)])
  (before (data-io/write-db data-spec/default-data))
  (data-spec/data-store-specs))