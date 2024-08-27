(ns main
  (:require [tic-tac-toe.play-game :as game]
            [tic-tac-toe.gui]))

(defn print-menu []
  (doseq [menu ["Would you like to play in the terminal or the gui?"
                "1. Terminal"
                "2. Gui"
                "3. Exit"]]
          (println menu)))

(defn -main [& args]
  (game/start-game args))