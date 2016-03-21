(defproject hobbit-hit "0.1.0-SNAPSHOT"
  :description "Hit hobbits, CFTBT Ch. 3"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot hobbit-hit.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
