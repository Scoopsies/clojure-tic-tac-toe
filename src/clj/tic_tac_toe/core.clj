(ns tic-tac-toe.core)

(defn switch-player [player]
  (if (= player "X") "O" "X"))