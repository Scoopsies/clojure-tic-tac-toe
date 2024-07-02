(ns tic-tac-toe.printables
  (:require [tic-tac-toe.board :as end-game]
            [tic-tac-toe.core :as core]))

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

(defn print-row [row-n board]
  (let [inc-board (map #(if (number? %) (inc %) %) board)
        row-values (nth (partition 3 inc-board) (dec row-n))]
    (println " " (nth row-values 0) " | " (nth row-values 1) " | " (nth row-values 2))))

(defn print-board [board]
  (println "")
  (print-row 1 board)
  (print-row 2 board)
  (print-row 3 board)
  (println ""))

(defn- print-win-message [player-token settings]
  (println (str ((settings player-token) :player-name) " wins!")))

(defn print-win-lose-draw [board settings]
  (cond
    (end-game/win? "X" board) (print-win-message "X" settings)
    (end-game/win? "O" board) (print-win-message "O" settings)
    (end-game/no-moves? board) (println "Draw.")))

(defn print-get-player-info [player-token]
  (println (str "Who will be playing as " player-token "?")))

(defn print-get-move-screen [board settings]
  (let [player-name ((settings (core/find-active-player board)) :player-name)]
    (print-board board)
    (println (str "It's " player-name "'s turn. Please pick a number 1-9."))))

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