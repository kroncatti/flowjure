(ns flowjure.routes.flow
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [flowjure.db.flow :as db.flow]
            [monger.collection :as mc]
            [flowjure.models.in.flow :as in.flow]))

(defn get-flow! [{{{:keys [database]} :db} :components
                  {:keys [id]}             :path-params}]
  (let [result (db.flow/retrieve-by-id! id database)]
    (if result
      {:status 200 :body (-> result
                             (dissoc :_id)
                             (assoc :id id))}
      {:status 404 :body "Not Found"})))

(defn post-flow! [{{{:keys [database]} :db} :components
                   data                     :data}]
  (let [id (random-uuid)
        payload (assoc data :_id id)]
    (db.flow/insert-flow! payload database)
    {:status 200 :body {:result :success
                        :id     id}}))

(def flow #{["/flow/:id"
             :get [interceptors.coercer/content-negotiation
                   interceptors.coercer/coerce-body!
                   get-flow!]
             :route-name ::get-flow]
            ["/flow"
             :post [interceptors.coercer/content-negotiation
                    interceptors.common/body-parser
                    (interceptors.coercer/coerce-body! in.flow/Flow)
                    post-flow!]
             :route-name ::post-flow]})
