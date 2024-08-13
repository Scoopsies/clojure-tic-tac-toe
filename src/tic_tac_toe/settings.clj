(ns tic-tac-toe.settings
  (:require [tic-tac-toe.printables :as printables]))

(defn get-player-name [player-token]
  (printables/print-get-player-name player-token)
  (read-line))

(defn get-difficulty-fn []
  (printables/print-get-difficulty-fn)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") :easy
      (= player-input "2") :medium
      (= player-input "3") :hard
      :else (do
              (printables/print-input-error player-input)
              (recur)))))

(defn get-move-fn [player-token]
  (printables/print-get-move-fn player-token)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") :human
      (= player-input "2") (get-difficulty-fn)
      :else (do
              (printables/print-input-error player-input)
              (recur player-token)))))

(defn get-player-settings [state]
  (let [player-token (if (state "X") "O" "X")]
    (let [move (get-move-fn player-token)
          player-name (if (= move :human)
                        (get-player-name player-token)
                        (str "Computer-" player-token))]
      (assoc state player-token {:move move :player-name player-name}))))

; am I getting the board size? - yes
; what did the user select (different between impls)
; map user input to selection 1 => (range 9)

(defn get-board [state]
  (printables/print-get-board-size)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") (assoc state :board-size :3x3)
      (= player-input "2") (assoc state :board-size :4x4)
      (= player-input "3") (assoc state :board-size :3x3x3)
      :else (do
              (printables/print-input-error player-input)
              (recur state)))))


