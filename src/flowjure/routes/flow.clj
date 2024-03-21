(ns flowjure.routes.flow
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.flow :as models.flow])
  (:import org.bson.types.ObjectId))

(defn get-flow! [{{{:keys [database]} :db} :components
                  {:keys [id]}             :path-params}]
  (let [result (mc/find-one-as-map database :flow {:_id (ObjectId. ^String id)})]
    (if result
      {:status 200 :body (-> result
                             (dissoc :_id)
                             (assoc :id id))}
      {:status 404 :body "Not Found"})))

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
