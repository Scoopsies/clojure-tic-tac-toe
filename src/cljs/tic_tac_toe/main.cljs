(ns tic-tac-toe.main
  (:require [reagent.dom :as rdom]
            [c3kit.wire.js :as wjs]))

(defn app []
  [:div
   {:id "bob"}
   [:a {:href "/"}]])

(defn ^:export main []
  (rdom/render [app] (wjs/element-by-id "app")))