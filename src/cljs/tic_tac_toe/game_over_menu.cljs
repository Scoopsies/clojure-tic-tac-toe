(ns tic-tac-toe.game-over-menu
  (:require [tic-tac-toe.play-gamec :as game]))

(defn- update-state [selection state]
  #(reset! state (game/get-next-state @state selection)))

(defn render-game-over [state]
  (let [printables (:printables @state)]
    [:div#game-over
     [:h2#winner (first printables)]
     [:h4#play-again "Play Again?"]
     [:button#-select-1
      {:on-click (update-state "1" state)}
      "1. Yes"]
     [:button#-select-2
      {:on-click (update-state "2" state)}
      "2. No"]
     ]))