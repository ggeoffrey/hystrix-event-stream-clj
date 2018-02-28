(defproject brainsfeed/hystrix-event-stream-clj "0.2.0"
  :monolith/inherit true
  :deployable true

  :description "Same as hystrix-event-streams-clj by Joseph Wilk but updated to manifold & clojure 1.9."
  :url "http://github.com/josephwilk/"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [com.netflix.hystrix/hystrix-clj "1.5.12"]
                 [com.netflix.hystrix/hystrix-metrics-event-stream "1.5.12"]
                 [manifold "0.1.6"]   ;; Replace lamina
                 [cheshire "5.8.0"]]

  :profiles {:dev {:dependencies [[midje "1.9.1"]]
                   :plugins      [[lein-midje "3.2.1"]
                                  [lein-cloverage "1.0.10"]
                                  [lein-kibit "0.1.6"]]}})
