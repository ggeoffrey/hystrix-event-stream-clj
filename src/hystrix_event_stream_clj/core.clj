(ns hystrix-event-stream-clj.core
  (:require
   [cheshire.core :as json]
   [hystrix-event-stream-clj.metrics :as metrics]
   [manifold.stream :as s]))

(def ^:const default-delay 2000)

(defn- write-metrics [ch]
  (try
    (s/put! ch (str "\nping: \n"))
    (doall (map #(s/put! ch (str "\ndata: " (json/encode %) "\n"))
                (metrics/commands)))
    (doall (map #(s/put! ch (str "\ndata: " (json/encode %) "\n"))
                (metrics/thread-pools)))
    true
    (catch java.io.IOException e
      false)
    (catch Exception e
      false)))

(defn- metric-streaming [ch]
  (future
    (loop [connected true]
      (Thread/sleep default-delay)
      (when connected (recur (write-metrics ch))))))

(defn- init-stream-channel [ch]
  ;; (receive-all ch (fn [_]))
  (metric-streaming ch))

(defn stream []
  (let [s (s/stream)]
    (init-stream-channel s)
    {:status 200 :body s :headers {"Content-Type"  "text/event-stream;charset=UTF-8"
                                   "Cache-Control" "no-cache, no-store, max-age=0, must-revalidate"
                                   "Pragma"        "no-cache"}}))
