(ns tic-tac-toe.menu
  (:require [c3kit.apron.corec :as ccc]
            [c3kit.wire.util :as util]
            [tic-tac-toe.play-gamec :as game]))

(defn update-state [selection state]
  #(reset! state (game/get-next-state @state selection)))

(defn get-menu [state]
  (let [[title & options :as printables] (:printables @state)]
    [:div#menu
     [:h2#-printable-0 title]
     [:<>
      (util/with-react-keys
        (ccc/for-all [n (range 1 (inc (count options)))]
            [:button {:id       (str "-select-" n)
                      :on-click (update-state (str n) state)}
             (nth printables n)]))]
     ]))