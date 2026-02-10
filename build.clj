(ns build
  (:require [clojure.tools.build.api :as b]))

(def class-dir "target/classes")
(def jar-file "target/TicTacToe.jar")

(defn jar [_]
  (b/delete {:path "target"})
  (b/copy-dir {:src-dirs ["src/clj" "src/cljc"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
           :jar-file jar-file})
  (println "Built" jar-file))
