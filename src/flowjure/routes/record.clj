(ns flowjure.routes.record
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.in.record :as in.record]))

(defn post-record! [{{{:keys [database]} :db} :components
                     data                     :data}]
  (let [id (random-uuid)
        payload (assoc data :_id id)
        flow (mc/find-one-as-map database :flow {:_id (parse-uuid (:flow-id payload))})]
    (if flow
      (do
        #_ (mc/insert-and-return database :record payload)
        (clojure.pprint/pprint payload)
        {:status 200 :body {:result :success
                            :id     id}})
      {:status 400 :body {:details {:type  :invalid-flow-id
                                    :error "flow-id was not found in database"}}})))

(def record #{["/record"
               :post [interceptors.coercer/content-negotiation
                      interceptors.common/body-parser
                      (interceptors.coercer/coerce-body! in.record/Record)
                      post-record!]
               :route-name ::post-record]})