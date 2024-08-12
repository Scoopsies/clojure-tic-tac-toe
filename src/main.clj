(ns main
  (:require [tic-tac-toe.play-game :as game]))

(defn -main [arg]
  (cond
    (= arg "-tui") (game/play-game {:ui :tui})
    (= arg "-gui") (game/play-game {:ui :gui})))
