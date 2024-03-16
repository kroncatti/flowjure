(ns flowjure.interceptors.coercer
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

(defn coerce-body-on-enter [context body-schema]
  (let [body (or (get-in context [:request :json-params]) (get-in context [:request :edn-params]))]
    (when body-schema
      (s/validate body-schema body))
    (if body
      (assoc-in context [:request :data] body)
      context)))

(defn coerce-body-on-leave [context]
  (if (get-in context [:response :headers "Content-Type"])
    context
    (update-in context [:response] coerce-to (accepted-type context))))

(defn coerce-body-on-error [context error]
  ; TODO: Move this to another interceptor and verify if error is indeed 400 before raising
  (assoc context :response (coerce-to {:status 400
                                       :body   {:details {:reason :schema-validation-error}}}
                                      (accepted-type context))))


(defn coerce-body!
  ([body-schema]
   (i/interceptor
     {:name  ::coerce-body!
      :enter (fn [context]
               (coerce-body-on-enter context body-schema))
      :leave coerce-body-on-leave
      :error coerce-body-on-error}))
  ([]
   (coerce-body! nil)))
