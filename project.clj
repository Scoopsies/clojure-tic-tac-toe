(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main main
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [quil/quil "4.3.1563"]]
  :profiles {:dev {:dependencies [[speclj "3.4.6"]]}}
  :plugins [[speclj "3.4.6"]]
  :test-paths ["spec"])
