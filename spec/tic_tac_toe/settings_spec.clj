(ns tic-tac-toe.settings-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.settings :as sut]
            [tic-tac-toe.moves.min-max :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.easy :as easy]))

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

  (context "get-difficulty"
    (it "returns update-board-hard if user selects 1"
      (with-redefs [println (stub :println)]
        (should= mini-max/update-board-hard (with-in-str "1" (sut/get-dificulty-fn)))))

    (it "returns update-board-medium if user selects 2"
      (with-redefs [println (stub :println)]
        (should= medium/update-board-medium (with-in-str "2" (sut/get-dificulty-fn)))))

    (it "returns update-board-easy if user selects 3"
      (with-redefs [println (stub :println)]
        (should= easy/update-board-easy (with-in-str "3" (sut/get-dificulty-fn)))))
    )

  (context "get-move-fn"
    (it "returns update-board-human if user selects 1"
      (with-redefs [println (stub :println)]
        (should= human-move/update-board-human
                 (with-in-str
                   "1"
                   (sut/get-move-fn "X")))))

    (it "returns update-board-hard if user selects 2 -> 1"
      (with-redefs [println (stub :println)]
        (should= mini-max/update-board-hard
                 (with-in-str
                   "2\n1"
                   (sut/get-move-fn "X")))))
    )

  (context "get-settings-player"
    (it "returns appropriate map for a human selection"
      (with-redefs [println (stub :println)]
        (should= {:move-fn human-move/update-board-human :player-name "Scoops"}
                 (with-in-str "1\nScoops" (sut/get-settings-player "X")))))

    (it "returns appropriate map for a computer selection"
      (with-redefs [println (stub :println)]
        (should= {:move-fn mini-max/update-board-hard :player-name "Computer-X"}
                 (with-in-str "2\n1" (sut/get-settings-player "X")))))
    )

  (context "get-all-settings"
    (it "gets settings for two computers"
      (with-redefs [println (stub :println)]
        (should= {"X" {:move-fn mini-max/update-board-hard :player-name "Computer-X"}
                  "O" {:move-fn mini-max/update-board-hard :player-name "Computer-O"}}
                 (with-in-str "2\n1\n2\n1" (sut/get-all-settings)))))

    (it "gets settings for a human and a computer"
      (with-redefs [println (stub :println)]
        (should= human-computer-settings
                 (with-in-str "1\nScoops\n2\n1" (sut/get-all-settings))))))
  )