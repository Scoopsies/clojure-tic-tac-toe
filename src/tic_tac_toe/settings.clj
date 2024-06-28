(ns tic-tac-toe.settings
  (:require [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.min-max :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]
            [tic-tac-toe.moves.medium :as medium]
            [tic-tac-toe.moves.easy :as easy]))

(defn get-player-name [player-token]
  (printables/get-player-name player-token)
  (read-line))

(defn get-dificulty []
  (println "Choose your dificulty level.")
  (println "1. Hard")
  (println "2. Medium")
  (println "3. Easy")
  (let [player-input (read-line)]
    (cond
      (= player-input "1") mini-max/update-board-hard
      (= player-input "2") medium/update-board-medium
      (= player-input "3") easy/update-board-easy)))

(defn get-move-fn [player-token]
  (printables/print-get-move-fn player-token)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") human-move/update-board-human
      (= player-input "2") (get-dificulty)
      :else (do
              (printables/print-input-error player-input)
              (recur player-token)))))

(defn get-settings-player [player-token]
  (let [move-fn (get-move-fn player-token)
        player-name (if (= move-fn human-move/update-board-human)
                      (get-player-name player-token)
                      (str "Computer-" player-token))]
    {:move-fn move-fn :player-name player-name}))

(defn get-all-settings []
  (let [x-settings (get-settings-player "X")
        o-settings (get-settings-player "O")]
    {"X" x-settings "O" o-settings}))

