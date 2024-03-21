(ns flowjure.routes.flow
  (:require [flowjure.interceptors.coercer :as interceptors.coercer]
            [flowjure.interceptors.common :as interceptors.common]
            [monger.collection :as mc]
            [flowjure.models.flow :as models.flow]))

(defn get-flow! [{{{:keys [database]} :db} :components
                  {:keys [id]}             :path-params}]
  ; TODO: parse-uuid on interceptor previously and return 400 if not properly formatted
  ; TODO: Check why not coercing to Accept
  (let [uuid (parse-uuid id)
        result (mc/find-one-as-map database :flow {:_id uuid})]
    (if result
      {:status 200 :body (-> result
                             (dissoc :_id)
                             (assoc :id id))}
      {:status 404 :body "Not Found"})))

(defn post-flow! [{{{:keys [database]} :db} :components
                   data                     :data}]
  (let [id (random-uuid)
        payload (assoc data :_id id :id id)]
    (let [result (mc/insert-and-return database :flow payload)]
      (clojure.pprint/pprint result))
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
                    (interceptors.coercer/coerce-body! models.flow/Flow)
                    post-flow!]
             :route-name ::post-flow]})
