(ns tic-tac-toe.main
  (:require [tic-tac-toe.play-gamec :as game]
            [tic-tac-toe.gui]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.state-initializerc :as state]
            [tic-tac-toe.data.edn-io]
            [tic-tac-toe.data.psql-io]))

(defn -main [& args]
  (let [initial-state (state/parse-args args) ui (:ui initial-state)]
    (if (= ui :tui) (println printables/title))
    (game/loop-game-play initial-state)))