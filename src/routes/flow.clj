(ns routes.flow
  (:require [interceptors.coercer :as interceptors.coercer]))


(defn get-flow! [{{:keys [db]} :components
                  :as          request}]
  (println db)
  {:status 200 :body request})

(def flow #{["/flow"
             :get [interceptors.coercer/coerce-body
                   interceptors.coercer/content-negotiation
                   get-flow!]
             :route-name ::flow]})
