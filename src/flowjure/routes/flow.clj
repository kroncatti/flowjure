(ns flowjure.routes.flow
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.flow :as models.flow]))

(defn get-flow! [{{{:keys [database]} :db} :components :as request}]
  (clojure.pprint/pprint (mc/find-maps database :users))
  {:status 200 :body request})

(defn post-flow! [{{{:keys [database]} :db} :components
                   data                     :data}]
  (let [id (random-uuid)
        payload (assoc data :_id id)]
    (mc/insert-and-return database :flow payload)
    {:status 200 :body {:result "success"
                        :id     id}}))

(def flow #{["/flow/:id"
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
