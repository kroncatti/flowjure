(ns routes.flow
  (:require [interceptors.coercer :as interceptors.coercer]
            [models.flow :as models.flow]))

(defn get-flow! [{{:keys [db]} :components
                  :as          request}]
  (println db)
  {:status 200 :body request})

(defn post-flow! [{{:keys [db]} :components
                   :as          request}]
  (println db)
  {:status 200 :body request})

(def flow #{["/flow"
             :get [interceptors.coercer/coerce-body-response
                   interceptors.coercer/content-negotiation
                   get-flow!]
             :route-name ::get-flow]
            ["/flow"
             :post [interceptors.coercer/coerce-body-response
                    interceptors.coercer/content-negotiation
                    (interceptors.coercer/coerce-body-request! models.flow/Flow)
                    post-flow!]
             :route-name ::post-flow]})
