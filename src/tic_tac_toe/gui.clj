(ns tic-tac-toe.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def window-size 500)
(def board-size 4)
(def square-size (/ window-size board-size))

(defn coordinate->pixel [n]
  (quot n square-size))

(defn draw-letter [letter x y]
  (let [X (* x (/ window-size board-size))
        Y (* (+ 1 y) (/ window-size board-size))]
    (q/text-size (+ square-size 70))
    (q/text letter X Y)))

(defn draw-square [x y]
  (q/fill (rand-int 250) (rand-int 250) (rand-int 250))
  (q/rect (* x (/ window-size board-size)) (* y (/ window-size board-size)) square-size square-size))

(defn draw-board [board-size]
  (doseq [x (range board-size)
          y (range board-size)]
    (draw-square x y)))

(defn draw [state]
  )

(defn next-state [state]
  (if (q/mouse-pressed?)
    (let [x (coordinate->pixel (q/mouse-x))
          y (coordinate->pixel (q/mouse-y))]
      (draw-letter "O" x y))))

(defn setup []
  (q/background 0 0 0)
  (q/frame-rate 30)
  (draw-board board-size)
  )

(declare gui)

(q/defsketch gui
  :title "Tic Tac Toe"
  :size [window-size window-size]
  :exit-on-close true
  :display 1
  :setup setup
  :draw draw
  :update next-state
  :middleware [m/fun-mode])

