(ns tic-tac-toe.settings
  (:require [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.hard :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.easy :as easy]))

(defn get-player-name [player-token]
  (printables/print-get-player-name player-token)
  (read-line))

(defn get-difficulty-fn []
  (printables/print-get-difficulty-fn)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") easy/update-board-easy
      (= player-input "2") medium/update-board-medium
      (= player-input "3") mini-max/update-board-hard
      :else (do
              (printables/print-input-error player-input)
              (recur)))))

(defn get-move-fn [player-token]
  (printables/print-get-move-fn player-token)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") human-move/update-board-human
      (= player-input "2") (get-difficulty-fn)
      :else (do
              (printables/print-input-error player-input)
              (recur player-token)))))

(defn get-player-settings [state]
  (let [player-token (if (state "X") "O" "X")]
    (let [move-fn (get-move-fn player-token)
          player-name (if (= move-fn human-move/update-board-human)
                        (get-player-name player-token)
                        (str "Computer-" player-token))]
      (assoc state player-token {:move-fn move-fn :player-name player-name}))))

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


