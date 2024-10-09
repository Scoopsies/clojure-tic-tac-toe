(ns tic-tac-toe.menu
  (:require [c3kit.apron.corec :as ccc]
            [c3kit.wire.util :as util]
            [tic-tac-toe.corejs :as corejs]))


(defn render-menu [state]
  (let [[title & options :as printables] (:printables @state)]
    [:div#menu
     [:h2#-printable-0 title]
     [:<>
      (util/with-react-keys
        (ccc/for-all [n (range 1 (inc (count options)))]
            [:button {:id       (str "-select-" n)
                      :on-click #(corejs/update-state (str n) state)}
             (nth printables n)]))]
     ]))

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