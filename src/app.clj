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

(defn service-map [env port]
  {:env          env
   ::http/routes app/routes
   ::http/type   :jetty
   ::http/port   port
   ::http/join?  false})
