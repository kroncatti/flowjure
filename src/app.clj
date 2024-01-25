(ns app
  (:require [clojure.set :as set]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [routes.flow :as routes.flow]
            [routes.record :as routes.record]))


(def routes
  (route/expand-routes
   (set/union routes.flow/flow
              routes.record/record)))

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890})

(defn start! []
  (http/start (http/create-server service-map)))

; Defining a state for the app
(defonce server (atom nil))

(defn start-dev! []
  (reset! server
          (http/start (http/create-server
                       (assoc service-map
                         ::http/join? false)))))

(defn stop-dev! []
  (when @server
    (http/stop @server)))

(defn restart! []
  (stop-dev!)
  (start-dev!))
(restart!)
