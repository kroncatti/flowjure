(ns interceptors.coercer
  (:require [io.pedestal.http.content-negotiation :as content-negotiation]
            [io.pedestal.interceptor :as i]
            [clojure.data.json :as json]
            [schema.core :as s]))

(def supported-types ["text/html"
                      "application/edn"
                      "application/json"
                      "text/plain"])

(defn accepted-type
  [context]
  (get-in context [:request :accept :field] "text/plain"))

(defn transform-content
  [body content-type]
  (case content-type
    "text/html" body
    "text/plain" body
    "application/edn" (pr-str body)
    "application/json" (json/write-str body)))

(defn coerce-to
  [response content-type]
  (-> response
      (update :body transform-content content-type)
      (assoc-in [:headers "Content-Type"] content-type)))

(def content-negotiation (content-negotiation/negotiate-content supported-types))

(defn coerce-body-request! [body-schema]
  (i/interceptor
   {:name ::coerce-body-request!
    :enter (fn [context]
             (if (get context :body)
               (do (s/validate body-schema (get context :body))
                   context)
               context))}))

(def coerce-body-response
  {:name  ::coerce-body-response
   :leave (fn [context]
            (if (get-in context [:response :headers "Content-Type"])
              context
              (update-in context [:response] coerce-to (accepted-type context))))})
