(ns tic-tac-toe.moves.check-all-moves-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.board :as board]))

(def state {"X" {:move :hard}
            "O" {:move :hard}
            :board-size :3x3
            :board (range 9)})

(defn get-next-moves [player-token board]
  (let [available-moves (board/get-available-moves board)
        active-player (board/get-active-player board)]

    (if (= active-player player-token)
      (map #(board/update-board % board) available-moves)
      [(board/update-board (tic-tac-toe.moves.mini-max/get-best-move {:board-size :3x3 :board board}) board)])))

(defn get-draws-and-losses [player-token board]
  (loop [finished-games [] in-progress-games [board]]
    (if (empty? in-progress-games)
      finished-games
      (recur
        (concat (filter board/game-over? in-progress-games) finished-games)
        (apply concat (map #(get-next-moves player-token %) (remove board/game-over? in-progress-games)))))))

(def get-draws-and-losses (memoize get-draws-and-losses))

(defn get-ai-losses [player-token board]
  (filter #(board/win? player-token %) (get-draws-and-losses player-token board)))


(describe "check-all-moves"

  (context "wins-everytime?"
    (it "hard mode never loses on 3x3 against O"
      (should (empty? (get-ai-losses "O" (range 9)))))

    (it "hard mode never loses on 3x3 against X"
      (should (empty? (get-ai-losses "X" (range 9)))))
    )
  )