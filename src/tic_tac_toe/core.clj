(ns tic-tac-toe.core)

(defn switch-player [player]
  (if (= player "X") "O" "X"))

(defn get-available-moves [board]
  (filter (partial number?) board))

(defmulti get-available-moves (fn [board] (count board)))

(defmethod get-available-moves :default [board]
  (filter (partial number?) board))

(defmethod get-available-moves 3 [board]
  (apply concat (map #(get-available-moves %) board)))

(defn find-active-player [board]
  (let [amount-x (count (filter (partial = "X") board))
        amount-o (count (filter (partial = "O") board))]
    (if (<= amount-x amount-o) "X" "O")))

(defn update-board
  ([selection board]
   (map #(if (= selection %) (find-active-player board) %) board))

  ([player-token selection board]
   (map #(if (= selection %) player-token %) board)))