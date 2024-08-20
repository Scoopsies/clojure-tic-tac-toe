(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as board]
            [clojure.string :as str]))

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

(defn- single-digit-num? [n]
  (and (number? n) (> n 8)))

(defn- space-row-values [rows]
  (map
    #(cond
       (single-digit-num? %) (str "  " (inc %) " ")
       (number? %) (str "  " (inc %) "  ")
       :else (str "  " % "  ")) rows))

(defn- ->rows [board]
  (partition (board/get-row-size board) board))

(defn- space-rows [rows]
  (map space-row-values rows))

(defn- stringify-rows [spaced-rows]
  (->> (map #(interpose "|" %) spaced-rows)
       (map str/join)))

(defmulti get-board-printables count)

(defmethod get-board-printables :default [board]
  (-> (->rows board)
      (space-rows)
      (stringify-rows)))

(defmethod get-board-printables 27 [board]
  (let [formatted-rows (->> (partition 9 board)
                            (map get-board-printables))]
    (for [row (range 3)]
      (->> (map #(nth % row) formatted-rows)
           (apply str)))))

(def player-x-printables ["Who will play as X?"
                          "1. Human"
                          "2. Computer Easy"
                          "3. Computer Medium"
                          "4. Computer Hard"])

(def player-o-printables ["Who will play as O?"
                          "1. Human"
                          "2. Computer Easy"
                          "3. Computer Medium"
                          "4. Computer Hard"])

(defmulti get-board-size-menu :ui)

(defmethod get-board-size-menu :tui [_]
  ["What size board?"
   "1. 3x3"
   "2. 4x4"
   "3. 3x3x3 (3-D)"])

(defmethod get-board-size-menu :gui [_]
  ["What size board?"
   "1. 3x3"
   "2. 4x4"])

(defn get-winner-printable [{:keys [board]}]
  (cond
    (board/win? "X" board) "X wins!"
    (board/win? "O" board) "O wins!"
    (board/no-moves? board) "Draw"))

(defn get-game-over-printable [state]
  [(get-winner-printable state)
   ""
   "Play Again?"
   "1. Yes"
   "2. No"])

(def continue-printables
  ["Continue last game?"
   "1. Yes"
   "2. No (new game)"])

(defn get-move-printables [board]
  (let [printable-board (get-board-printables board)]
    (conj (vec printable-board) "" (str "Please pick a number 1-" (count board) "."))))