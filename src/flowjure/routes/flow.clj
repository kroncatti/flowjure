(ns flowjure.routes.flow
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.flow :as models.flow]))

(defn get-flow! [{{:keys [db]} :components
                  :as          request}]
  (clojure.pprint/pprint (mc/find-maps (:database db) :users))
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
