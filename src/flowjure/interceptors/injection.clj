(ns flowjure.interceptors.injection
  (:require
   [io.pedestal.interceptor :as i]))

(defn injection [app]
  (i/interceptor
   {:name  ::injection
    :enter (fn [context] (assoc-in context [:request :components] app))}))
