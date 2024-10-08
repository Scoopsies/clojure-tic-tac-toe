(ns tic-tac-toe.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.boardc :as board]
            [tic-tac-toe.moves.corec :as move]
            [tic-tac-toe.play-gamec :as play-game]))

(def window-size 500)

(defmulti get-square-size (fn [board-size] board-size))

(defmethod get-square-size :3x3 [_]
  (/ window-size 3))

(defmethod get-square-size :4x4 [_]
  (/ window-size 4))

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

(defn render-game [state]
  (do
    (q/background 250 250 250)
    (draw-board state)
    (draw-placements state)))

(defn- menu-screen?
  ([state _] (menu-screen? state))

  ([{:keys [board game-over?]}]
   (or (not board) game-over?)))

(defn draw [state]
  (cond
    (:end-game? state) (q/exit)
    (menu-screen? state) (render-menu state)
    :else (render-game state)))

(defn ai-turn? [state]
  (let [{:keys [board]} state]
    (and board (not= :human (state (board/get-active-player board))))))

(defn get-selection [state selection]
  (if (and (ai-turn? state) (not (:game-over? state)))
    (move/pick-move state)
    selection))

(defn next-state [state]
  (let [{:keys [selection]} state state (dissoc state :selection)]
    (let [selection (get-selection state selection)]
      (play-game/get-next-state state selection))))

(defmulti set-selection menu-screen?)

(defn in-area-n? [n y]
  (let [n (* n 60)]
    (and (> y (+ 30 n)) (< y (+ 60 n)))))

(defmulti menu-select :game-over?)

(defmethod menu-select :default [_ y]
  (cond
    (in-area-n? 1 y) "1"
    (in-area-n? 2 y) "2"
    (in-area-n? 3 y) "3"
    (in-area-n? 4 y) "4"
    :else nil))

(defmethod menu-select true [_ y]
  (cond
    (in-area-n? 3 y) "1"
    (in-area-n? 4 y) "2"
    :else nil))

(defmethod set-selection true [state [_ y]]
  (menu-select state y))



(defn pixel->coordinate [n square-size]
  (quot n square-size))

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

(defn setup [state]
  (q/frame-rate 60)
  state)

(declare tic-tac-toe)

(defmethod play-game/loop-game-play :gui [state]
  (q/defsketch tic-tac-toe
    :title "Tic Tac Toe"
    :size [window-size window-size]
    :exit-on-close true
    :display 1
    :setup (partial setup state)
    :draw draw
    :update next-state
    :mouse-clicked handle-click
    :middleware [m/fun-mode]))