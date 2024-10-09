(ns tic-tac-toe.main
  (:require [c3kit.wire.js :as wjs]
            [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [tic-tac-toe.state-initializerc :as state]
            [tic-tac-toe.menu :as menu]
            [tic-tac-toe.board :as board]))

(def state (reagent/atom (state/parse-args ["--tui"])))

(defn game-in-progress? [state]
  (and (:board @state) (not (:game-over? @state))))

(defn app []
  [:div#content
   [:div {:id "title"} [:h1 "Tic-Tac-Toe"]]
   (if (game-in-progress? state)
     (board/render-board state)
     (menu/get-menu state))]
  )

(defn ^:export main []
  (rdom/render [app] (wjs/element-by-id "app")))