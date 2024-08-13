(ns tic-tac-toe.settings-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.settings :as sut]))

(describe "settings"
  (with-stubs)

  (context "get-player-name"
    (it "returns user-input for name"
      (with-redefs [println (stub :println)]
        (should= "Scoops" (with-in-str "Scoops" (sut/get-player-name "X")))
        (should= "Alex" (with-in-str "Alex" (sut/get-player-name "O")))
        (should= "Micah" (with-in-str "Micah" (sut/get-player-name "X")))
        (should= "ham sandwich" (with-in-str "ham sandwich" (sut/get-player-name "O")))))
    )

  (context "get-difficulty-fn"
    (it "returns update-board-easy if user selects 1"
      (with-redefs [println (stub :println)]
        (should= :easy  (with-in-str "1" (sut/get-difficulty-fn)))))

    (it "returns update-board-medium if user selects 2"
      (with-redefs [println (stub :println)]
        (should= :medium (with-in-str "2" (sut/get-difficulty-fn)))))

    (it "returns update-board-hard if user selects 3"
      (with-redefs [println (stub :println)]
        (should= :hard (with-in-str "3" (sut/get-difficulty-fn)))))
    )

  (context "get-move-fn"
    (it "returns update-board-human if user selects 1"
      (with-redefs [println (stub :println)]
        (should= :human
                 (with-in-str "1" (sut/get-move-fn "X")))))

    (it "returns update-board-easy if user selects 2 -> 1"
      (with-redefs [println (stub :println)]
        (should= :easy
                 (with-in-str "2\n1" (sut/get-move-fn "X")))))
    )

  (context "get-settings-player"
    (it "returns appropriate map for a human selection"
      (with-redefs [println (stub :println)]
        (should= {:ui :tui
                  "X" {:move :human :player-name "Scoops"}}
                 (with-in-str "1\nScoops" (sut/get-player-settings {:ui :tui})))))

    (it "returns appropriate map for a computer selection"
      (with-redefs [println (stub :println)]
        (should= {:ui :tui
                  "X" {:move :hard :player-name "Computer-X"}}
                 (with-in-str "2\n3" (sut/get-player-settings {:ui :tui})))))
    )

  (context "get-board-size"
    (it "returns a 3x3 if option 1 is selected"
      (with-redefs [println (stub :println)]
        (should= {:board-size :3x3} (with-in-str "1" (sut/get-board {})))))

    (it "returns a 4x4 if option 2 is selected"
      (with-redefs [println (stub :println)]
        (should= {:board-size :4x4} (with-in-str "2" (sut/get-board {})))))

    (it "returns a 3x3x3 if option 3 is selected"
      (with-redefs [println (stub :println)]
        (should= {:board-size :3x3x3} (with-in-str "3" (sut/get-board {})))))
    )

  (context "get-difficulty-fn"
    (redefs-around [println (stub :println)])
    (it "returns easy if user selects 1"
      (should= :easy (with-in-str "1" (sut/get-difficulty-fn))))

    (it "returns medium if user selects 2"
      (should= :medium (with-in-str "2" (sut/get-difficulty-fn))))

    (it "returns mini-max if user selects 3"
      (should= :hard (with-in-str "3" (sut/get-difficulty-fn))))
    )
  )