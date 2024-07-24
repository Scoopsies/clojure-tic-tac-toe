(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.board :as end-game]
            [tic-tac-toe.core :as core]
            [clojure.string :as str]))

(def title
  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗
  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝
     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗
     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝
     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗
     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝"
  )

(defn print-title []
  (println title))

(defn print-valid-move-error [player-move]
  (println (str player-move " is not a valid move"))
  (println "Please enter a valid move."))

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
       (map ->rows)
       (map space-rows)
       (map stringify-rows)))

(defmulti print-board (fn [board] (count board)))

(defmethod print-board :default [board]
    (println "")
    (run! println (format-rows board))
    (println ""))

(defmethod print-board 27 [board]
  (let [formatted-rows (format-rows board)]
    (doseq [row (range 3)]
      (->> (map #(nth % row) formatted-rows)
           (apply str)
           (println)))))

(defn- print-win-message [player-token settings]
  (println (str ((settings player-token) :player-name) " wins!")))

(defn print-win-lose-draw [board settings]
  (cond
    (end-game/win? "X" board) (print-win-message "X" settings)
    (end-game/win? "O" board) (print-win-message "O" settings)
    (end-game/no-moves? board) (println "Draw.")))

(defn print-get-player-info [player-token]
  (println (str "Who will be playing as " player-token "?")))

(defn make-possessive [player-name]
  (if (= \s (last player-name))
    (str player-name  "'")
    (str player-name "'s")))

(defn print-get-move [board settings]
  (let [player-name ((settings (core/find-active-player board)) :player-name)]
    (println (str "It's " (make-possessive player-name) " turn. Please pick a number 1-" (count board) "."))))

(defn print-get-player-name [player-token]
  (println (str "What is the name of player " player-token "?")))

(defn print-get-move-fn [player-token]
  (doseq [lines [(str "Choose who will play as " player-token ".")
                 "1. Human"
                 "2. Computer"]]
    (println lines)))

(defn print-get-dificulty-fn []
  (doseq [lines ["Choose your dificulty level."
                 "1. Hard"
                 "2. Medium"
                 "3. Easy"]]
    (println lines)))

(defn print-get-board-size []
  (doseq [lines ["What size board would you like to play on? (3 or 4 currently supported)"
                 "1. 3x3"
                 "2. 4x4"
                 "3. 3x3x3 (3-D)"]]
    (println lines)))