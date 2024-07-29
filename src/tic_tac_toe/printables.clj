(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.board :as end-game]
            [tic-tac-toe.core :as core]
            [clojure.string :as str]
            [tic-tac-toe.moves.human-move :as human-move]))

(def title
  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗
  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝
     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗
     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝
     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗
     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝"
  )

(defn print-formatted [printables]
  (doseq [lines printables]
    (println lines))
  (println ""))

(defn print-title []
  (print-formatted [title]))

(defn print-input-error [player-input]
  (println player-input "is not a valid input."))

(defn- single-digit-num? [n]
  (and (number? n) (> n 8)))

(defn- space-row-values [rows]
  (map
    #(cond
       (single-digit-num? %) (str "  " (inc %) " ")
       (number? %) (str "  " (inc %) "  ")
       :else (str "  " % "  ")) rows))

(defn- ->rows [board]
  (partition (board/count-rows board) board))

(defn- space-rows [rows]
  (map space-row-values rows))

(defn stringify-rows [spaced-rows]
  (map #(-> (interleave % (repeat (dec (count %)) "|")) (concat [(last %)]) (str/join)) spaced-rows))

(defmulti format-rows count)

(defmethod format-rows :default [board]
  (-> (->rows board)
      (space-rows)
      (stringify-rows)))

(defmethod format-rows 27 [board]
  (->> (partition 9 board)
       (map format-rows)))

(defmulti print-board (fn [board] (count board)))

(defmethod print-board :default [board]
  (print-formatted (format-rows board)))

(defmethod print-board 27 [board]
  (println "")
  (let [formatted-rows (format-rows board)]
    (doseq [row (range 3)]
      (->> (map #(nth % row) formatted-rows)
           (apply str)
           (println))))
  (println ""))

(defn- get-win-message [player-token settings]
  (str ((settings player-token) :player-name) " wins!"))

(defn print-win-lose-draw [board settings]
  (let [win-x (get-win-message "X" settings) win-o (get-win-message "O" settings)]
    (cond
      (end-game/win? "X" board) (print-formatted [win-x])
      (end-game/win? "O" board) (print-formatted [win-o])
      (end-game/no-moves? board) (print-formatted ["Draw."]))))

(defn print-get-player-info [player-token]
  (let [printable [(str "Who will be playing as " player-token "?")]]
    (print-formatted printable)))

(defn make-possessive [player-name]
  (if (= \s (last player-name))
    (str player-name  "'")
    (str player-name "'s")))

(defmulti print-get-move
  (fn [board settings]
    (let [active-player (core/find-active-player board)]
      (:move-fn (settings active-player)))))

(defmethod print-get-move :default [board settings]
  (let [active-player (core/find-active-player board)
        name (:player-name (settings active-player))
        name's (make-possessive name)]
    (print-formatted
      [(str "It's " name's " turn.")])))

(defmethod print-get-move human-move/update-board-human [board settings]
  (let [active-player (core/find-active-player board)
        name (:player-name (settings active-player))
        name's (make-possessive name)]
    (print-formatted
      [(str "It's " name's " turn.")
       (str "Please pick a number 1-" (count board) ".")])))

(defn print-get-player-name [player-token]
  (let [printable [(str "What is the name of player " player-token "?")]]
    (print-formatted printable)))

(defn print-get-move-fn [player-token]
  (print-formatted [(str "Choose who will play as " player-token ".")
          "1. Human"
          "2. Computer"]))

(defn print-get-difficulty-fn []
  (print-formatted ["Choose your difficulty level."
          "1. Easy"
          "2. Medium"
          "3. Hard"]))

(defn print-get-board-size []
  (print-formatted ["What size board would you like to play on? (3 or 4 currently supported)"
          "1. 3x3"
          "2. 4x4"
          "3. 3x3x3 (3-D)"]))