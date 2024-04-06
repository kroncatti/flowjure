(ns flowjure.routes.flow
  (:require
   [flowjure.controllers.flow :as controllers.flow]
   [flowjure.interceptors.coercer :as interceptors.coercer]
   [flowjure.interceptors.common :as interceptors.common]
   [flowjure.models.in.flow :as in.flow]))

(defn get-flow! [{{{:keys [database]} :db} :components
                  {:keys [id]}             :path-params}]
  (if-let [result (controllers.flow/retrieve-by-id! id database)]
    {:status 200 :body result}
    {:status 404 :body "Not Found"}))

(defn post-flow! [{{{:keys [database]} :db} :components
                   data                     :data}]
  (let [id (random-uuid)]
    (controllers.flow/insert! id data database)
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
