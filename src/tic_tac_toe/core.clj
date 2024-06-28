(ns tic-tac-toe.core)

(defn switch-player [player]
  (if (= player "X") "O" "X"))

(defn get-available-moves [board]
  (filter (partial number?) board))

(defn find-active-player [board]
  (let [amount-x (count (filter (partial = "X") board))
        amount-o (count (filter (partial = "O") board))]
    (if (<= amount-x amount-o) "X" "O")))

(defn update-board [selection board]
  (map #(if (= selection %) (find-active-player board) %) board))