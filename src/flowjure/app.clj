(ns flowjure.app
  (:require
   [clojure.set :as set]
   [flowjure.routes.flow :as routes.flow]
   [flowjure.routes.record :as routes.record]
   [flowjure.routes.admin :as routes.admin]
   [io.pedestal.http :as http]
   [io.pedestal.http.route :as route]))

(def routes
  (route/expand-routes
   (set/union
    routes.flow/flow
    routes.record/record
    routes.admin/admin)))

(defn service-map [env port]
  {:env          env
   ::http/routes routes
   ::http/type   :jetty
   ::http/port   port
   ::http/join?  false})
