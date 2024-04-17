(ns flowjure.routes.admin
  (:require
    [flowjure.interceptors.coercer :as interceptors.coercer]))

(defn ping! [_context]
  {:status 200
   :body   "pong!"})


(def admin #{["/admin/health"
              :get [interceptors.coercer/content-negotiation
                    (interceptors.coercer/coerce-body!)
                    ping!]
              :route-name ::get-flow]})