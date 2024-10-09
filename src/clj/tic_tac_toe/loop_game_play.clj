(ns tic-tac-toe.loop-game-play
  (:require [tic-tac-toe.moves.corec :as move]
            [tic-tac-toe.play-gamec :as play-game]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.moves.human-move]))

(defmulti loop-game-play :ui)

(defn get-selection [state]
  (cond
    (:end-game? state) nil
    (and (:board state) (not (:game-over? state))) (move/pick-move state)
    :else (read-line)))

(defmethod loop-game-play :tui [state]
  (printables/print-formatted (:printables state))
  (let [selection (get-selection state) updated-state (play-game/get-next-state state selection)]
    (if (:end-game? state)
      nil
      (recur updated-state))))