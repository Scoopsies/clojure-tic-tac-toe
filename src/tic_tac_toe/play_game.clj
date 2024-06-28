(ns tic-tac-toe.play-game
  (:require [tic-tac-toe.core :as core]
            [tic-tac-toe.settings :as settings]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.printables :as printables]
            [clojure.string :as str]))

(declare play-game)

(defn- valid-input? [player-input]
  (not-empty (filter #(= player-input %) ["y" "yes" "no" "n"])))

(defn play-again? []
  (println "Would you like to play again? Y/N")
  (let [player-input (str/lower-case (read-line))]
    (if (valid-input? player-input)
      (or (= player-input "yes")
          (= player-input "y"))
      (recur))))

(defn print-game-over [board settings]
  (printables/print-board board)
  (printables/print-win-lose-draw board settings)
  (if (play-again?)
    (play-game)
    (println (str "See you next time!"))))

(defn- get-move-choice [board settings]
  (printables/print-get-move-screen board settings)
  (let [active-player (core/find-active-player board)
        move-fn ((settings active-player) :move-fn)]
    (play-game (move-fn board) settings)))

(defn play-game
  ([]
   (printables/print-title)
   (play-game (range 9) (settings/get-all-settings)))

  ([board settings]
   (if (board/game-over? board)
     (print-game-over board settings)
     (get-move-choice board settings))))
;Í
;; One option:
;(defmulti squak :breed)
;(defmethod squak :pigeon [bird]
;  )
;(defmethod squak :ostrich [bird]
;  )
;
;
;(defmulti render :state)
;(defmethod render :new-game [state]
;  (prn "New game!"))
;(defmethod render :board [state]
;  (prn "1 2 3\n4 5 ..."))
;
;(defmulti next-state :state)
;(defmethod next-state :new-game [state selection]
;  (assoc state :state ((:options state) selection)))
;
;(defn play []
;  (loop [cur-state {:state :new-game :options {1 :hvh 2 :hvc ...}}]
;    (render cur-state)
;    (let [selection (get-input)]
;      (recur (next-state cur-state selection)))))
;
;; Another option:
;
;(defn next-state [settings next-fn render-fn]
;  (let [state ]))
;
;(defn token-menu [settings]
;  )
;
;(defn difficulty-menu [settings]
;  (let [state (assoc settings :next-fn token-menu
;                              :render-fn render-token-menu)]
;    ((:render-fn state))
;    (let [selection (get-input)]
;      ((:next-fn state) selection))))
;
;(defn play []
;  (let [state {:move-fn     human-move/update-board-human
;               :player-name "Scoops"
;               :render-fn   ()
;               :next-fn     difficulty-menu}]
;    ((:render-fn state))
;    (let [selection (get-input)]
;      ((:next-fn state) selection))))