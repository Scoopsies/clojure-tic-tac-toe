(ns main
  (:require [tic-tac-toe.play-game :as game]
            [tic-tac-toe.gui]))

(defn -main [& args]
  (game/start-game args))