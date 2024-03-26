(ns flowjure.routes.record
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.record :as models.record]))

(defn post-record! [{{{:keys [database]} :db} :components
                     data                     :data}]
  (let [id (random-uuid)
        payload (assoc data :_id id)]
    (mc/insert-and-return database :record payload)
    {:status 200 :body {:result :success
                        :id     id}}))

(def record #{["/record"
               :post [interceptors.coercer/content-negotiation
                      interceptors.common/body-parser
                      (interceptors.coercer/coerce-body! models.record/Record)
                      post-record!]
               :route-name ::post-record]})