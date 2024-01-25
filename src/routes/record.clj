(ns routes.record
  (:require [interceptors.coercer :as interceptors.coercer]))


(defn get-record [request]
  (println request)
  {:status 200 :body {:flow "1234"}})

(def record #{["/record"
               :get [interceptors.coercer/coerce-body
                     interceptors.coercer/content-negotiation
                     get-record]
               :route-name ::record]})
