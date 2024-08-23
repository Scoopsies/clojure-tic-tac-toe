(ns tic-tac-toe.state-initializer
  (:require [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.board :as board]))

(defn retrieve-game [id]
  (nth (data/read-db) id))

(defn initialize-replay [id]
  (let [{:keys [board-size ui move-order]} (retrieve-game id)
        new-board (board/create-board board-size)]
    {:board-size board-size
     :ui ui
     :move-order []
     :board new-board
     :printables (printables/get-move-printables new-board)
     "X" {:move :replay}
     "O" {:move :replay}
     :replay-moves move-order
     :game-over? false}))

(defn- unfinished? [last-game]
  (not (or (not last-game) (board/game-over? (:board last-game)))))

(defn handle-last-game []
  (let [last-game (data/pull-last-db)]
    (if (unfinished? last-game) last-game nil)))

(defmulti ->initial-state :replay?)

(defmethod ->initial-state :default [state]
  (let [last-game (handle-last-game)]
    (if last-game
      (assoc state
        :printables printables/continue-printables
        :last-game last-game
        :game-over? false)
      (assoc state
        :printables printables/player-x-printables
        :game-over? false))))

(defmethod ->initial-state true [state]
  (let [id (:id state) state (dissoc state :id)]
    (merge (initialize-replay id) state)))