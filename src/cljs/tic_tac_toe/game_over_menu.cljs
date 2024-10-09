(ns tic-tac-toe.game-over-menu
  (:require [tic-tac-toe.corejs :as corejs]))

(defn render-game-over [state]
  (let [printables (:printables @state)]
    [:div#game-over
     [:h2#winner (first printables)]
     [:h4#play-again "Play Again?"]
     [:button#-select-1
      {:on-click #(corejs/update-state "1" state)}
      "1. Yes"]
     [:button#-select-2
      {:on-click #(corejs/update-state "2" state)}
      "2. No"]
     ]))