(ns ticket
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.content-negotiation :as content-negotiation]))

(def supported-types ["text/html"
                      "application/edn"
                      "application/json"
                      "text/plain"])

(defn respond-ticket [request]
  {:status 200 :body request})


(def routes
  (route/expand-routes
   #{["/ticket"
      :get [(content-negotiation/negotiate-content supported-types)
            respond-ticket]
      :route-name ::ticket]}))

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
