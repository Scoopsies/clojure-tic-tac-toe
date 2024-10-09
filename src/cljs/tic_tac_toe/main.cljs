(ns tic-tac-toe.main
  (:require [c3kit.wire.js :as wjs]
            [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [tic-tac-toe.printablesc :as printables]
            [tic-tac-toe.menu :as menu]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.moves.easyc]
            [tic-tac-toe.moves.mediumc]
            [tic-tac-toe.moves.hardc]
            [tic-tac-toe.moves.replayc]))

(def state (reagent/atom {:printables printables/player-x-printables}))

(defn game-in-progress? [state]
  (and (:board @state) (not (:game-over? @state))))

(defn app []
  [:div#content
   [:div {:id "title"} [:h1 "Tic-Tac-Toe"]]
   (cond
     (game-in-progress? state) (board/render-board state)
     (:game-over? @state) (menu/render-game-over state)
     :else (menu/render-menu state))]
  )

(defn ^:export main []
  (rdom/render [app] (wjs/element-by-id "app")))