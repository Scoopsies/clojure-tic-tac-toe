(ns tic-tac-toe.settings
  (:require [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.hard :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.easy :as easy]))

(defn get-player-name [player-token]
  (printables/get-player-name player-token)
  (read-line))

(defn get-dificulty-fn []
  (printables/print-get-dificulty-fn)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") mini-max/update-board-hard
      (= player-input "2") medium/update-board-medium
      (= player-input "3") easy/update-board-easy
      :else (do
              (printables/print-input-error player-input)
              (recur)))))

(defn get-move-fn [player-token]
  (printables/print-get-move-fn player-token)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") human-move/update-board-human
      (= player-input "2") (get-dificulty-fn)
      :else (do
              (printables/print-input-error player-input)
              (recur player-token)))))

(defn get-settings-player [player-token]
  (let [move-fn (get-move-fn player-token)
        player-name (if (= move-fn human-move/update-board-human)
                      (get-player-name player-token)
                      (str "Computer-" player-token))]
    {:move-fn move-fn :player-name player-name}))

(defn get-board-size []
  (println "What size board would you like to play on? (3 or 4 currently supported)")
  (println "1) 3x3")
  (println "2) 4x4")
  (println "3) 3x3x3 (3-D)")
  (let [player-input (read-line)]
    (cond
      (= player-input "1") (range 9)
      (= player-input "2") (range 16)
      (= player-input "3") (range 27)
      :else (do
              (printables/print-input-error player-input)
              (recur)))))

(defn get-all-settings []
  (let [x-settings (get-settings-player "X")
        o-settings (get-settings-player "O")
        board (get-board-size)]
    {"X" x-settings
     "O" o-settings
     :board board}))
