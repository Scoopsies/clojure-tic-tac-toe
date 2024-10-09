(ns tic-tac-toe.board
  (:require [c3kit.apron.corec :as ccc]
            [c3kit.wire.util :as util]
            [tic-tac-toe.play-gamec :as game]
            [tic-tac-toe.moves.corec :as move]))

(defn- update-state [selection state]
  #(reset! state (game/get-next-state @state selection)))

(defn- format-value [board n]
  (if (number? (nth board n))
    (inc (nth board n))
    (nth board n)))

(defn- computer-turn? [state]
  (not (= :human (move/get-move-param @state))))

(defn render-board [state]
  (let [dref-state @state
        board (:board dref-state)
        board-size (name (:board-size dref-state))]

    (when (computer-turn? state)
      (js/setTimeout
        (update-state (move/pick-move @state) state)
        1000))

    [:div {:id "board"
           :class (str "board" board-size)}
     [:<>
      (util/with-react-keys
        (ccc/for-all [n (range (count board))]
          [:button {:id (str "-cell-" n)
                    :class (str "cell" board-size)
                    :on-click (update-state (nth board n) state)}
           (format-value board n)]))]]))