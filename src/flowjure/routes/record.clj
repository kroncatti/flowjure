(ns flowjure.routes.record
  (:require
   [flowjure.controllers.record :as controllers.record]
   [flowjure.interceptors.coercer :as interceptors.coercer]
   [flowjure.interceptors.common :as interceptors.common]
   [flowjure.models.in.record :as in.record]))

(defn get-record! [{{:keys [db]} :components
                    {:keys [id]} :path-params}]
  (if-let [result (controllers.record/retrieve-by-id! id db)]
    {:status 200 :body result}
    {:status 404 :body "Not Found"}))

(defn post-record! [{{:keys [db]} :components
                     data         :data}]
  (let [id (random-uuid)
        result (controllers.record/insert! id data db)]
    (if (= :non-existing-flow-id result)
      {:status 400 :body {:details {:type  :invalid-flow-id
                                    :error "flow-id was not found in database"}}}
      {:status 200 :body {:result :success
                          :id     id}})))

(def record #{["/record/:id"
               :get [interceptors.coercer/content-negotiation
                     (interceptors.coercer/coerce-body!)
                     get-record!]
               :route-name ::get-record]
              ["/record"
               :post [interceptors.coercer/content-negotiation
                      interceptors.common/body-parser
                      (interceptors.coercer/coerce-body! in.record/Record)
                      post-record!]
               :route-name ::post-record]})