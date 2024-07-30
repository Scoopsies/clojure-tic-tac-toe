(ns tic-tac-toe.moves.check-all-moves-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.hard :as hard]))

(defn get-next-moves [player-token board ai-fn]
  (let [available-moves (core/get-available-moves board)]
    (if (= (core/get-active-player board) player-token)
      (map #(core/update-board % board) available-moves)
      [(ai-fn board)])))

(defn get-draws-and-losses [player-token board ai-fn]
  (loop [finished-games [] in-progress-games [board]]
    (if (empty? in-progress-games)
      finished-games
      (recur
        (concat (filter board/game-over? in-progress-games) finished-games)
        (apply concat (map #(get-next-moves player-token % ai-fn) (remove board/game-over? in-progress-games)))))))

(def get-draws-and-losses (memoize get-draws-and-losses))

(defn get-ai-losses [player-token board ai-fn]
  (filter #(board/win? player-token %) (get-draws-and-losses player-token board ai-fn)))


(describe "check-all-moves"
  (context "get-finished-games"

    (it "gives list of all games win mini-max is O"
      (should= 569
               (count (get-draws-and-losses "X" (range 9) hard/update-board-hard))))

    (it "gives a list of all games when mini-max is X"
      (should= 73
               (count (get-draws-and-losses "O" (range 9) hard/update-board-hard))))

    (it "gives list of all games when ai-x is medium"
      (should= 649 (count (get-draws-and-losses "X" (range 9) medium/update-board-medium))))
    )

  (context "wins-everytime?"
    (it "returns true for all games minimax plays on 3x3 against O"
      (should (empty? (get-ai-losses "O" (range 9) hard/update-board-hard))))

    (it "returns false for all games medium plays on 3x3 against X"
      (should-not (empty? (get-ai-losses "X" (range 9) medium/update-board-medium))))

    (it "returns false for all games medium plays on 3x3 against O"
      (should-not (empty? (get-ai-losses "O" (range 9) medium/update-board-medium))))
    )
  )