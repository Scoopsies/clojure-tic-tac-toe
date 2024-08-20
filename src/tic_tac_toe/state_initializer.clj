(ns tic-tac-toe.state-initializer
  (:require [tic-tac-toe.data.data-io :as data]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.board :as board]))

(defn retrieve-game [id]
  (nth (data/read) id))

(defn initialize-data [id]
  (let [{:keys [board-size ui move-order]} (retrieve-game id)
        new-board (board/create-board board-size)]
    {:board-size board-size
     :ui ui
     :move-order []
     :board new-board
     :printables (printables/get-move-printables new-board)
     "X" {:move :replay}
     "O" {:move :replay}
     :replay-moves move-order}))