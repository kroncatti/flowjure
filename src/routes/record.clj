(ns routes.record
  (:require [interceptors.coercer :as interceptors.coercer]))


(defn get-record! [request]
  {:status 200 :body request})

(def record #{["/record"
               :get [interceptors.coercer/coerce-body
                     interceptors.coercer/content-negotiation
                     get-record!]
               :route-name ::record]})
