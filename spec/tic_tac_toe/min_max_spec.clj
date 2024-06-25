(ns tic-tac-toe.min-max-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.min-max :as sut]
            [clojure.math.combinatorics :as combos]))

(declare min-max-loss?)

(describe "mini-max"

  (context "get-best-move"
    (it "takes a win over a block"
      (should= 2 (sut/get-best-move ["O" "O" 2 "X" "X" 5 "X" 7 8]))
      (should= 0 (sut/get-best-move [0 1 "X" "O" 4 "X" "O" "X" 8])))

    (it "takes a block if no win is available"
      (should= 2 (sut/get-best-move ["X" "X" 2 3 "O" 5 6 7 8]))
      (should= 8 (sut/get-best-move [0 1 2 3 "O" 5 "X" "X" 8])))

    (it "plays the middle if corner is played first"
      (should= 4 (sut/get-best-move ["X" 1 2 3 4 5 6 7 8]))
      (should= 4 (sut/get-best-move [0 1 "X" 3 4 5 6 7 8]))
      (should= 4 (sut/get-best-move [0 1 2 3 4 5 "X" 7 8]))
      (should= 4 (sut/get-best-move [0 1 2 3 4 5 6 7 "X"]))
      )

    (it "plays the corner if middle is played first"
      (should= 8 (sut/get-best-move [0 1 2 3 "X" 5 6 7 8])))
    )

  (context "min-max-move"
    (it "scores a win for maximizer"
      (should= 9 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] true 0)))

    (it "scores a win for minimizer"
      (should= -9 (sut/min-max-move ["X" "O" "X" "O" "X" "O" "O" "X" 8] false 0)))

    (it "scores a Draw for maximizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] true 0)))

    (it "scores a Draw for minimizer"
      (should= 0 (sut/min-max-move [0 "O" "X" "X" "X" "O" "O" "X" "O"] false 0)))
    )
  )

(defn- not-valid-move? [moves board]
  (not (some #(= % (first moves)) (core/get-available-moves board))))

(defn- terminate? [moves board]
  (or
    (board/game-over? board)
    (not-valid-move? moves board)))

(defn- player-board [player-token board moves]
  (core/update-board player-token (first moves) board))

(defn- min-max-board [player-token board]
  (core/update-board (core/switch-token player-token) (sut/get-best-move board) board))

(defn- play-as [player-token player-moves board]
  (if (= player-token (core/find-active-player board))
    (min-max-loss? (core/switch-token player-token) (rest player-moves) (player-board player-token board player-moves))
    (min-max-loss? (core/switch-token player-token) player-moves (min-max-board player-token board))))

(defn min-max-loss?
  ([player-token player-moves] (min-max-loss? player-token player-moves (range 9)))

  ([player-token player-moves board]
   (if (terminate? player-moves board)
     (board/win? player-token board)
     (play-as player-token player-moves board))))

(def all-game-combos-x (set (map #(drop 4 %) (combos/permutations (range 9)))))
(def all-game-combos-O (set (map #(drop 3 %) (combos/permutations (range 9)))))

(defn check-all-games [ai-token]
  (if (= ai-token "X")
    (empty? (filter #(min-max-loss? "O" %) all-game-combos-O))
    (empty? (filter #(min-max-loss? "X" %) all-game-combos-x))))

(describe "Checking that get-best-move never loses"
  (tags :slow)
  (context "min-max-loss?"
    (it "doesn't lose against player moves 0 1 5"
      (should-not (min-max-loss? "X" [0 1 5])))

    (it "doesn't lose against player moves 4 2 1"
      (should-not (min-max-loss? "X" [4 2 1])))

    (it "returns false if move was already played by mini-max"
      (should-not (min-max-loss? "X" [0 4 5])))
    )

  (context "check-all-games"
    (it (str "returns true if none of the " (count all-game-combos-x) " possible games where mini-max is O are lost.")
      (should (check-all-games "O")))

    (it (str "returns true if none of the " (count all-game-combos-O) " possible games where mini-max is X are lost.")
      (should (check-all-games "X")))
    )
  )