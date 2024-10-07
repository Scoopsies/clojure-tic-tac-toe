(ns tic-tac-toe.corec)

(defn switch-player [player]
  (if (= player "X") "O" "X"))