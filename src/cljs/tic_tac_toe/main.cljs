(ns tic-tac-toe.main
  (:require [c3kit.wire.js :as wjs]
            [reagent.core :as reagent]
            [reagent.dom :as rdom]
            [tic-tac-toe.play-gamec :as game]))

(def state (reagent/atom (game/get-next-state)))

(defn app []
  [:div
   {:id "title"}
   [:h1 "Tic-Tac-Toe"]
   [:p#-printable-0 "Who will play as X?"]
   [:p#-select-1 "1. Human"]
   [:p#-select-2 "2. Computer Easy"]
   [:p#-select-3 "3. Computer Medium"]
   ]
  )
   ;[:button#-select-0 {:on-click #(swap! state gamec/next-state 0
                                    ;...
                                    ;)}]])

(defn ^:export main []
  (rdom/render [app] (wjs/element-by-id "app")))