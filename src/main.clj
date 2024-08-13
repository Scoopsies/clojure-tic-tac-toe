(ns main
  (:require [tic-tac-toe.play-game :as game]))

(defn -main [arg]
  (cond
    (= arg "-tui") (game/start-game {:ui :tui})
    (= arg "-gui") (game/start-game {:ui :gui})))
