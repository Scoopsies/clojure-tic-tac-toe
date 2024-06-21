(ns tic-tac-toe.ui.play-game
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]
            [tic-tac-toe.min-max :refer :all]
            [clojure.string :as str]))

(declare play-game-human-first)
(declare play-game)

(def title
  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗
  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝
     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗
     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝
     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗
     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝"
  )

(defn get-player-name []
  (println title)
  (println "What is your name?")
  (read-line))

(defn print-row [row-n board]
  (let [row-values (nth (partition 3 board) (dec row-n))]
    (println " " (nth row-values 0) " | " (nth row-values 1) " | " (nth row-values 2))))

(defn print-board [board]
  (let [board (map #(if (number? %) (inc %) %) board)]
    (print-row 1 board)
    (print-row 2 board)
    (print-row 3 board)))

(defn switch-token [token]
  (if (= token "X") "O" "X"))

(defn print-win-lose-draw [player-name board player-token]
  (cond
    (win? player-token board) (println (str "You win! Good Job " player-name "!"))
    (win? (switch-token player-token) board) (println (str "You lose. Better luck next-time " player-name "."))
    (no-moves? board) (println (str "Draw. You almost had it " player-name "."))))

(defn play-again? []
  (println "Would you like to play again? Y/N")
  (let [player-answer (str/lower-case (read-line))]
    (if (not-empty (filter #(= player-answer %) ["y" "yes" "no" "n"]))
      (or (= player-answer "yes")
          (= player-answer "y"))
      (recur))))

(defn print-game-over [player-name board player-token]
  (do (print-board board)
      (print-win-lose-draw player-name board player-token)
      (if (play-again?)
        (play-game player-name)
        (println (str "See you next time " player-name "!")))))

(defn- valid-move? [valid-moves player-move]
  (some #(= player-move %) valid-moves))

(defn get-player-move [board]
  (let [player-move (read-line)
        valid-moves (map #(str (inc %)) (get-available-moves board))]
    (if (valid-move? valid-moves player-move)
      (dec (Integer/parseInt player-move))
      (do
        (println (str player-move " is not a valid move"))
        (println "Please enter a valid move.")
        (recur board)))))

(defn human-select [board player-name x-turn?]
  (print-board board)
  (println (str "It's your turn " player-name ". Please pick a number 1-9."))
  (let [token (if x-turn? "X" "O")]
    (update-board token (get-player-move board) board)))

(defn cpu-select [board player-token]
  (let [cpu-token (switch-token player-token)]
    (update-board cpu-token (get-best-move board) board)))

(defn get-player-token []
  (println "Would you like to be X (first) or O (second)?")
  (let [player-input (str/lower-case (read-line))]
    (cond
      (= player-input "x") "X"
      (= player-input "o") "O"
      :else (do
              (println (str player-input " is not a valid answer."))
              (recur)))))

(defn play-game-human-first
  ([player-name] (play-game-human-first true player-name (range 9) (get-player-token)))

  ([x-turn? player-name board player-token]
     (if (game-over? board)
       (print-game-over player-name board player-token)
       (if x-turn?
         (recur (not x-turn?) player-name (human-select board player-name x-turn?) player-token)
         (recur (not x-turn?) player-name (cpu-select board player-token) player-token)))))

(defn play-game-cpu-first
  ([player-name] (play-game-cpu-first true player-name (range 9) (get-player-token)))

  ([x-turn? player-name board player-token]
   (if (game-over? board)
     (print-game-over player-name board player-token)
     (if x-turn?
       (recur (not x-turn?) player-name (cpu-select board player-token) player-token)
       (recur (not x-turn?) player-name (human-select board player-name x-turn?) player-token)))))

(defn play-game
  ([]
   (let [player-name (get-player-name)
         player-token (get-player-token)]
     (if (= player-token "X")
       (play-game-human-first true player-name (range 9) player-token)
       (play-game-cpu-first true player-name (range 9) player-token))))

  ([player-name]
   (let [player-token (get-player-token)]
     (if (= player-token "X")
       (play-game-human-first true player-name (range 9) player-token)
       (play-game-cpu-first true player-name (range 9) player-token)))))