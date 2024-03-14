(ns flowjure.app
  (:require [clojure.set :as set]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [flowjure.routes.flow :as routes.flow]))

(def routes
  (route/expand-routes
   (set/union routes.flow/flow)))

(defn service-map [env port]
  {:env          env
   ::http/routes routes
   ::http/type   :jetty
   ::http/port   port
   ::http/join?  false})
