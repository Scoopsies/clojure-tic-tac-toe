(ns tic-tac-toe.settings
  (:require [clojure.string :as str]))

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

(defn get-player-token []
  (println "Would you like to be X (first) or O (second)?")
  (let [player-input (str/lower-case (read-line))]
    (cond
      (= player-input "x") "X"
      (= player-input "o") "O"
      :else (do
              (println (str player-input " is not a valid answer."))
              (recur)))))