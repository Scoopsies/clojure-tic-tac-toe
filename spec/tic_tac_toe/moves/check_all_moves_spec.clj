(ns tic-tac-toe.moves.check-all-moves-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.hard :as hard]
            [clojure.math.combinatorics :as combos]))

(declare game-loss?)

(defn- not-valid-move? [moves board]
  (not (some #(= % (first moves)) (core/get-available-moves board))))

(defn- terminate? [moves board]
  (or
    (board/game-over? board)
    (not-valid-move? moves board)))

(defn- player-board [board moves]
  (core/update-board (first moves) board))

(defn- play-as [player-token player-moves ai-logic board]
  (if (= player-token (core/find-active-player board))
    (game-loss? player-token (rest player-moves) ai-logic (player-board board player-moves))
    (game-loss? player-token player-moves ai-logic (ai-logic board))))

(defn game-loss?
  ([player-token player-moves ai-logic] (game-loss? player-token player-moves ai-logic (range 9)))

  ([player-token player-moves ai-logic board]
   (if (terminate? player-moves board)
     (board/win? player-token board)
     (play-as player-token player-moves ai-logic board))))

(def all-game-combos-x (set (map #(drop 4 %) (combos/permutations (range 9)))))
(def all-game-combos-O (set (map #(drop 3 %) (combos/permutations (range 9)))))

(defn check-all-games [ai-token ai-logic]
  (if (= ai-token "X")
    (count (filter #(game-loss? "O" % ai-logic) all-game-combos-O))
    (count (filter #(game-loss? "X" % ai-logic) all-game-combos-x))))

(describe "check-all-games (medium)"
  (tags :slow)
  (it (str "231/" (count all-game-combos-O) " games lost where AI is O.")
    (should= 231 (check-all-games "O" medium/update-board-medium)))

  (it (str "140/" (count all-game-combos-x) " games lost where AI is X.")
    (should=  140 (check-all-games "X" medium/update-board-medium)))
  )

(describe "check-all-games (hard)"
  (tags :slow)
  (it (str "0/" (count all-game-combos-O) " games lost where AI is O.")
    (should= 0 (check-all-games "O" hard/update-board-hard)))

  (it (str "0/" (count all-game-combos-x) " games lost where AI is X.")
    (should= 0 (check-all-games "X" hard/update-board-hard)))
  )