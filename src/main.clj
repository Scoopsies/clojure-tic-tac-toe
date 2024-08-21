(ns main
  (:require [tic-tac-toe.play-game :as game]
            [tic-tac-toe.gui]))

(def title
  "  ████████╗██╗ ██████╗    ████████╗ █████╗  ██████╗    ████████╗ ██████╗ ███████╗
  ╚══██╔══╝██║██╔════╝    ╚══██╔══╝██╔══██╗██╔════╝    ╚══██╔══╝██╔═══██╗██╔════╝
     ██║   ██║██║            ██║   ███████║██║            ██║   ██║   ██║█████╗
     ██║   ██║██║            ██║   ██╔══██║██║            ██║   ██║   ██║██╔══╝
     ██║   ██║╚██████╗       ██║   ██║  ██║╚██████╗       ██║   ╚██████╔╝███████╗
     ╚═╝   ╚═╝ ╚═════╝       ╚═╝   ╚═╝  ╚═╝ ╚═════╝       ╚═╝    ╚═════╝ ╚══════╝"
  )

(defn print-menu []
  (doseq [menu ["Would you like to play in the terminal or the gui?"
                "1. Terminal"
                "2. Gui"
                "3. Exit"]]
          (println menu)))

(defn- valid-game-flag? [args]
  (and (= (first args) "--game") (second args)))

(defn get-starter-state
  ([args]
   (let [first-flag (first args)]
     (cond
       (= first-flag "--tui") (get-starter-state {:ui :tui} (rest args))
       (= first-flag "--gui") (get-starter-state {:ui :gui} (rest args))
       :else (throw (Exception. (str first-flag " is not a valid first argument."))))))

  ([state args]
   (cond
     (empty? (first args)) state
     (valid-game-flag? args) (merge state {:replay? true :id (Integer/parseInt (second args))})
     :else (throw (Exception. "Invalid flag or game-id")))))

(defn -main
  ([]
   (print-menu)
   (let [selection (read-line)]
     (cond
       (= selection "1") (-main "--tui")
       (= selection "2") (-main "--gui")
       (= selection "3") (println "Exiting...")
       :else (recur))))

  ([& args]
   (let [start-state (get-starter-state args) ui (:ui start-state)]
     (if (= ui :tui) (println title))
     (game/start-game start-state))))