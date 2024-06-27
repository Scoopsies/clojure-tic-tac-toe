(ns tic-tac-toe.settings
  (:require [clojure.string :as str]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.moves.min-max :as mini-max]
            [tic-tac-toe.moves.human-move :as human-move]))

(defn get-player-name [player-token]
  (printables/get-player-name player-token)
  (read-line))

(defn get-player-token []
  (println "Would you like to be X (first) or O (second)?")
  (let [player-input (str/lower-case (read-line))]
    (cond
      (= player-input "x") "X"
      (= player-input "o") "O"
      :else (do
              (printables/print-input-error player-input)
              (recur)))))

(defn get-move-fn [player-token]
  (printables/print-get-move-fn player-token)
  (let [player-input (read-line)]
    (cond
      (= player-input "1") human-move/update-board-human
      (= player-input "2") mini-max/update-board-hard
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