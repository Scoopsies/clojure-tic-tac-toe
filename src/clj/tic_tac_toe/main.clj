(ns tic-tac-toe.main
  (:require [tic-tac-toe.loop-game-play :as game]
            [tic-tac-toe.gui]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.state-initializer :as state]
            [tic-tac-toe.data.edn-io]
            [tic-tac-toe.data.psql-io]
            [tic-tac-toe.moves.easyc]
            [tic-tac-toe.moves.mediumc]
            [tic-tac-toe.moves.hardc]
            [tic-tac-toe.moves.replayc]))

(defn -main [& args]
  (let [initial-state (state/parse-args args) ui (:ui initial-state)]
    (if (= ui :tui) (println printables/title))
    (game/loop-game-play initial-state)))