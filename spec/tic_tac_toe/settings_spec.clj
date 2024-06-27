(ns tic-tac-toe.settings-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.settings :as sut]
            [tic-tac-toe.moves.min-max :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]))

(def human-computer-settings
  {"X" {:move-fn human-move/update-board-human :player-name "Scoops"}
   "O" {:move-fn mini-max/update-board-hard :player-name "Computer-O"}})

(describe "settings"
  (with-stubs)

  (context "get-player-name"
    (it "returns user-input for name"
      (with-redefs [println (stub :println)]
        (should= "Scoops" (with-in-str "Scoops" (sut/get-player-name "X")))
        (should= "Alex" (with-in-str "Alex" (sut/get-player-name "O")))
        (should= "Micha" (with-in-str "Micha" (sut/get-player-name "X")))
        (should= "ham sandwich" (with-in-str "ham sandwich" (sut/get-player-name "O")))))
    )

  (context "get-player-token"
    (it "returns X if x is input"
      (with-redefs [println (stub :println)]
        (should= "X" (with-in-str "x" (sut/get-player-token)))
        (should= "X" (with-in-str "X" (sut/get-player-token)))))
    (it "returns O if o is input"
      (with-redefs [println (stub :println)]
        (should= "O" (with-in-str "O" (sut/get-player-token)))
        (should= "O" (with-in-str "o" (sut/get-player-token)))))
    (it "prints asking for input."
      (should= "Would you like to be X (first) or O (second)?\n"
               (with-out-str
                 (with-in-str "o" (sut/get-player-token)))))
    )

  (context "get-settings-player"
    (it "returns appropriate map for a human selection"
      (with-redefs [println (stub :println)]
        (should= {:move-fn human-move/update-board-human :player-name "Scoops"}
                 (with-in-str "1\nScoops" (sut/get-settings-player "X")))))

    (it "returns appropriate map for a computer selection"
      (with-redefs [println (stub :println)]
        (should= {:move-fn mini-max/update-board-hard :player-name "Computer-X"}
                 (with-in-str "2" (sut/get-settings-player "X")))))
    )

  (context "get-all-settings"
    (it "gets settings for two computers"
      (with-redefs [println (stub :println)]
        (should= {"X" {:move-fn mini-max/update-board-hard :player-name "Computer-X"}
                  "O" {:move-fn mini-max/update-board-hard :player-name "Computer-O"}}
                 (with-in-str "2\n2" (sut/get-all-settings)))))

    (it "gets settings for a human and a computer"
      (with-redefs [println (stub :println)]
        (should= human-computer-settings
                 (with-in-str "1\nScoops\n2" (sut/get-all-settings))))))
  )