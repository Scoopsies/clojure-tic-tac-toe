(ns tic-tac-toe.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.moves.core :as move]
            [tic-tac-toe.printables :as printables]
            [tic-tac-toe.play-game :as play-game]))

(def window-size 500)

(defmulti get-square-size (fn [board-size] board-size))

(defmethod get-square-size :3x3 [_]
  (/ window-size 3))

(defmethod get-square-size :4x4 [_]
  (/ window-size 4))

(defn pixel->coordinate [n square-size]
  (quot n square-size))

(defn draw-letter [letter [x y] board-size]
  (let [square-size (get-square-size board-size)]
    (let [X (* x square-size)
          Y (* (+ 1 y) square-size)]
      (q/fill 255 0 0)
      (q/text-size (+ square-size 70))
      (q/text letter X Y))))

(defn draw-square [[x y] board-size]
  (let [square-size (get-square-size board-size)]
    (q/fill 0 0 0)
    (q/rect (* x square-size) (* y square-size) (- square-size 3) (- square-size 3))))

(defmulti draw-board :board-size)

(defmethod draw-board :3x3 [{:keys [board-size]}]
  (doseq [x (range 3)
          y (range 3)]
    (draw-square [x y] board-size)))

(defmethod draw-board :4x4 [{:keys [board-size]}]
  (doseq [x (range 4)
          y (range 4)]
    (draw-square [x y] board-size)))

(defn draw-placements [{:keys [board board-size]}]
  (let [grid (board/->grid board)
        row-size (board/get-row-size board)]
    (doseq [x (range row-size)
            y (range row-size)
            :when (string? (nth (nth grid y) x))]
      (let [value (nth (nth grid y) x)]
        (draw-letter value [x y] board-size)))))

(defn render-menu-line [printables n]
  (q/text (nth printables n) 30 (+ (* n 60) 60)))

(defn render-menu [state]
  (let [printables (:printables state)]
    (do
      (q/background 0 0 0)
      (q/fill 255 255 255)
      (q/text-size 50)
      (run! #(render-menu-line printables %) (range (count printables))))))

(defn draw [state]
  (cond
    (:end-game? state) (q/exit)
    (not (:board state)) (do
                           (q/background 0 0 0)
                           (render-menu state))

    (not (:game-over? state)) (do
                          (q/background 250 250 250)
                          (draw-board state)
                          (draw-placements state))
    :else (render-menu state)))

(defn ai-turn? [state]
  (let [{:keys [board]} state]
    (and board (not= :human (:move (state (core/get-active-player board)))))))

(defn get-selection [state selection]
  (if (and (ai-turn? state) (not (:game-over? state)))
    (move/pick-move state)
    selection))

(defn next-state [state]
  (let [{:keys [selection]} state state (dissoc state :selection)]
    (let [selection (get-selection state selection)]
      (play-game/next-state state selection))))

(defn setup []
  (q/frame-rate 60)
  {:ui :gui
   :menu? true
   :end-game? false
   :printables printables/player-x-printables})

(defmulti set-selection :menu?)

(defmethod set-selection true [_ [_ y]]
  (cond
    (and (> y 90) (< y 120)) "1"
    (and (> y 151) (< y 180)) "2"
    (and (> y 213) (< y 240)) "3"
    (and (> y 270) (< y 300)) "4"
    :else nil))

(defmethod set-selection false [state [x y]]
  (let [square-size (get-square-size (:board-size state))
        grid (board/->grid (:board state))
        X (pixel->coordinate x square-size)
        Y (pixel->coordinate y square-size)
        selection (nth (nth grid Y) X)]
    (if (number? selection)
      selection
      nil)))


(defn handle-click [state, {:keys [x y]}]
  (assoc state :selection (set-selection state [x y])))

(declare tic-tac-toe)

(defn start-gui []
  (q/defsketch tic-tac-toe
    :title "Tic Tac Toe"
    :size [window-size window-size]
    :exit-on-close true
    :display 1
    :setup setup
    :draw draw
    :update next-state
    :mouse-clicked handle-click
    :middleware [m/fun-mode]))

(defmethod play-game/loop-game-play :gui [_]
  (start-gui))