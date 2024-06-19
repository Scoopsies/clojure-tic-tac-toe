(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.core :refer :all]
            [tic-tac-toe.end-game :refer :all]
            [tic-tac-toe.min-max :refer :all]))

(declare play-game)

(defn get-player-name []
  (println "████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗\n╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝\n   ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗  \n   ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝  \n   ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗\n   ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝\n                                                                               ")
  (println "What is your name?")
  (read-line))

(defn print-board [board]
  (let [board (map #(if (number? %) (inc %) %) board)]
    (println "")
    (println " " (nth board 0) " | " (nth board 1) " | " (nth board 2))
    (println " " (nth board 3) " | " (nth board 4) " | " (nth board 5))
    (println " " (nth board 6) " | " (nth board 7) " | " (nth board 8))))

(defn print-win-lose-draw [player-name board]
  (cond
    (win? "X" board) (do
                       (print-board board)
                       (println (str "\nGood Job " player-name ", You win!")))
    (win? "O" board) (do
                       (print-board board)
                       (println (str "\nBetter luck next-time " player-name ". You lose.")))
    (no-moves? board) (do
                        (print-board board)
                        (println (str "\nYou almost had it " player-name ". Draw.")))))

(defn play-again? []
  (do (println "\nWould you like to play again? Y/N")
      (let [player-answer (read-line)]
        (or (= player-answer "Yes")
            (= player-answer "YES")
            (= player-answer "yes")
            (= player-answer "Y")
            (= player-answer "y")
            ))))

(defn print-game-over [player-name board]
  (do
    (print-win-lose-draw player-name board)
    (if (play-again?)
      (play-game player-name)
      (println "See you next time!"))))

(defn play-game
  ([] (play-game (get-player-name) (range 0 9)))

  ([player-name] (play-game player-name (range 0 9)))

  ([player-name board]
   (do
     (print-board board)
     (println (str "\nIt's your turn " player-name ". Please pick a number 1-9."))
     (if (game-over? board)
       (print-game-over player-name board)
       (let [player-move (dec (Integer/parseInt (read-line)))
             player-board (update-board "X" player-move board)]
         (if (game-over? player-board)
           (recur player-name player-board)
           (recur player-name (update-board "O" (get-best-move "O" player-board) player-board))))))))
