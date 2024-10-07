(ns tic-tac-toe.main
  (:require [tic-tac-toe.play-game :as game]
            [tic-tac-toe.gui]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.state-initializer :as state]
            [tic-tac-toe.data.edn-io]
            [tic-tac-toe.data.psql-io])
  (:import (tic_tac_toe.data.edn_io EdnIO)
            (tic_tac_toe.data.psql_io PsqlIO)))

(defn -main [& args]
  (let [initial-state (state/parse-args args) ui (:ui initial-state)]
    (if (= ui :tui) (println printables/title))
    (game/loop-game-play initial-state)))
