(ns tic-tac-toe.core)

(defn update-board [player-token selection board]
  (map #(if (= selection %) player-token %) board))

(defn get-available-moves [board]
  (filter (partial number?) board))

(defn find-active-player [board]
  (let [amount-x (count (filter (partial = "X") board))
        amount-o (count (filter (partial = "O") board))]
    (if (<= amount-x amount-o) "X" "O")))

(defn switch-token [token]
  (if (= token "X") "O" "X"))