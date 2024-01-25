(ns routes.flow
  (:require [interceptors.coercer :as interceptors.coercer]))


(defn get-flow [request]
  (println request)
  {:status 200 :body {:flow "1234"}})

(def flow #{["/ticket"
             :get [interceptors.coercer/coerce-body
                   interceptors.coercer/content-negotiation
                   get-flow]
             :route-name ::flow]})
