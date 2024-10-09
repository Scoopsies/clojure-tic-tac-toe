(ns tic-tac-toe.board
  (:require [c3kit.apron.corec :as ccc]
            [c3kit.wire.util :as util]
            [tic-tac-toe.corejs :as corejs]
            [tic-tac-toe.moves.corec :as move]))

(defn- format-value [board n]
  (if (number? (nth board n))
    (inc (nth board n))
    (nth board n)))

(defn- human-turn? [state]
  (= :human (move/get-move-param @state)))

(defn- computer-turn? [state]
  (not (human-turn? state)))

(defn- clickable? [state board n]
  (and (human-turn? state) (number? (nth board n))))

(defn handle-click [state n]
  (let [board (:board @state)]
    (if (clickable? state board n)
      (corejs/update-state (nth board n) state)
      nil)))

(defn set-timeout [fn ms]
  (js/setTimeout fn ms))

(defn take-computer-turn [state]
  (set-timeout #(corejs/update-state (move/pick-move @state) state) 1000))

(defn render-board [state]
  (let [dref-state @state
        board (:board dref-state)
        board-size (name (:board-size dref-state))]

    (when (computer-turn? state)
      (take-computer-turn state))

    [:div {:id "board"
           :class (str "board" board-size)}
     [:<>
      (util/with-react-keys
        (ccc/for-all [n (range (count board))]
          [:button {:id (str "-cell-" n)
                    :class (str "cell" board-size)
                    :on-click #(handle-click state n)}
           (format-value board n)]))]]))