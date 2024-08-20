(ns main
  (:require [tic-tac-toe.play-game :as game]
            [tic-tac-toe.gui]))


(defn print-menu []
  (doseq [menu ["Would you like to play in the terminal or the gui?"
                "1. Terminal"
                "2. Gui"
                "3. Exit"]]
          (println menu)))

(defn -main
  ([]
   (print-menu)
   (let [selection (read-line)]
     (cond
       (= selection "1") (-main "-tui")
       (= selection "2") (-main "-gui")
       (= selection "3") (println "Exiting...")
       :else (recur))))

  ([arg]
   (cond
     (= arg "-tui") (game/start-game {:ui :tui})
     (= arg "-gui") (game/start-game {:ui :gui})
     :else (throw (Exception. (str arg " is not a valid argument."))))))
