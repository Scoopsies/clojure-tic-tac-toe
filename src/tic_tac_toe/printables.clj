(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as end-game]
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

(defn space-row-values [rows]
  (map
    #(cond
       (and (number? %) (> % 8)) (str "  " (inc %) " ")
       (number? %) (str "  " (inc %) "  ")
       :else (str "  " % "  ")) rows))

(defn stringify-rows [spaced-rows]
  (map #(str/join (concat (interleave % (repeat (dec (count %)) "|")) [(last %)])) spaced-rows))

(defn print-board [board]
  (let [rows (partition (int (Math/sqrt (count board))) board)
        spaced-rows (map space-row-values rows)
        formatted-rows (stringify-rows spaced-rows)]
    (println "")
    (run! println formatted-rows)
    (println "")))

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

(defn get-player-name [player-token]
  (println (str "What is the name of player " player-token "?")))

(defn print-get-move-fn [player-token]
  (println "Choose who will play as " player-token ".")
  (println "1. Human")
  (println "2. Computer"))

(defn print-input-error [player-input]
  (println player-input " is not a valid input."))

(defn print-get-dificulty-fn []
  (println "Choose your dificulty level.")
  (println "1. Hard")
  (println "2. Medium")
  (println "3. Easy"))