(ns tic-tac-toe.moves.medium-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.moves.medium :as sut]
            [tic-tac-toe.core :as core]
            [clojure.math.combinatorics :as combos]
            [tic-tac-toe.board :as board]))

(describe "medium"
  (context "get-medium-move"
    (it "returns index of winning move"
      (should= 2
               (sut/get-medium-move ["X" "X" 2 "O" "O" 5 6 7 8])))

    (it "returns index of blocking move if winning move isn't available"
      (should= 2
               (sut/get-medium-move ["X" "X" 2
                                     "O" 4 5
                                     "O" 7 "X"])))
    )

  (context "take-block"
    (it "tests"
      (should= 2
               (sut/take-block "O" ["X" "X" 2
                                    "O" 4 5
                                    "O" 7 "X"]))))

  (context "win-next-turn?"
    (it "returns true for win from x in next turn"
      (should (sut/win-next-turn? "X" ["X" "X" 2 "O" "O" 5 6 7 8])))

    (it "returns true for win from o in next turn"
      (should (sut/win-next-turn? "O" ["X" 1 2
                                       "O" "O" 5
                                       "X" 7 8])))
    )

  (context "take-block"
    (it "returns index of blocking move"
      (should= 2 (sut/take-block "X" ["O" "O" 2 "X" "X" 5 6 7 8]))))

  )

(declare medium-loss?)

(defn- not-valid-move? [moves board]
  (not (some #(= % (first moves)) (core/get-available-moves board))))

(defn- terminate? [moves board]
  (or
    (board/game-over? board)
    (not-valid-move? moves board)))

(defn- player-board [board moves]
  (core/update-board (first moves) board))

(defn- medium-move-board [board]
  (core/update-board (sut/get-medium-move board) board))

(defn- play-as [player-token player-moves board]
  (if (= player-token (core/find-active-player board))
    (medium-loss? player-token (rest player-moves) (player-board board player-moves))
    (medium-loss? player-token player-moves (medium-move-board board))))

(defn medium-loss?
  ([player-token player-moves] (medium-loss? player-token player-moves (range 9)))

  ([player-token player-moves board]
   (if (terminate? player-moves board)
     (board/win? player-token board)
     (play-as player-token player-moves board))))

(def all-game-combos-x (set (map #(drop 4 %) (combos/permutations (range 9)))))
(def all-game-combos-O (set (map #(drop 3 %) (combos/permutations (range 9)))))

(defn check-all-games [ai-token]
  (if (= ai-token "X")
    (count (filter #(medium-loss? "O" %) all-game-combos-O))
    (count (filter #(medium-loss? "X" %) all-game-combos-x))))

(describe "check-all-games (medium)"


  (it (str "231/" (count all-game-combos-O) " games lost where AI is O.")
    (should= 231 (check-all-games "O")))

  (it (str "140/" (count all-game-combos-x) " games lost where AI is X.")
    (should=  140 (check-all-games "X"))))