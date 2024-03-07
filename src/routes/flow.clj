(ns routes.flow
  (:require [interceptors.coercer :as interceptors.coercer]
            [interceptors.common :as interceptors.common]
            [models.flow :as models.flow]))

(defn get-flow! [{{:keys [db]} :components
                  :as          request}]
  (println db)
  {:status 200 :body request})

(defn post-flow! [{{:keys [db]} :components
                   :as          request}]
  (clojure.pprint/pprint (:json-params request))
  {:status 200 :body (:json-params request)})

(def flow #{["/flow"
             :get [interceptors.coercer/content-negotiation
                   interceptors.coercer/coerce-body!
                   get-flow!]
             :route-name ::get-flow]
            ["/flow"
             :post [interceptors.coercer/content-negotiation
                    interceptors.common/body-parser
                    (interceptors.coercer/coerce-body! models.flow/Flow)
                    post-flow!]
             :route-name ::post-flow]})
