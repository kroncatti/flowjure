(ns interceptors.inject
  (:require [io.pedestal.interceptor :as i]))

(defn inject [app]
  (i/interceptor
   {:name  ::inject
    :enter (fn [context] (assoc-in context [:request :components] app))}))
