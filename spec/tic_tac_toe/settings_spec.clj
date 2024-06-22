(ns tic-tac-toe.settings-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.settings :as sut]))

(describe "settings"
  (with-stubs)

  (context "get-player-name"
    (it "returns user-input for name"
      (with-redefs [println (stub :println)]
        (should= "Scoops" (with-in-str "Scoops" (sut/get-player-name)))
        (should= "Alex" (with-in-str "Alex" (sut/get-player-name)))
        (should= "Micha" (with-in-str "Micha" (sut/get-player-name)))
        (should= "ham sandwich" (with-in-str "ham sandwich" (sut/get-player-name)))))
    )

  (context "get-player-token"
    (it "returns X if x is input"
      (with-redefs [println (stub println)]
        (should= "X" (with-in-str "x" (sut/get-player-token)))
        (should= "X" (with-in-str "X" (sut/get-player-token)))))

    (it "returns O if o is input"
      (with-redefs [println (stub println)]
        (should= "O" (with-in-str "O" (sut/get-player-token)))
        (should= "O" (with-in-str "o" (sut/get-player-token)))))

    (it "prints asking for input."
      (should= "Would you like to be X (first) or O (second)?\n"
               (with-out-str
                 (with-in-str "o" (sut/get-player-token)))))
    )
  )