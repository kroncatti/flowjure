(ns integration.helpers.helpers
  (:require [cheshire.core :as json]
            [clojure.string :as string]
            [com.stuartsierra.component :as component]
            [io.pedestal.test :as pedestal.test]
            [state-flow.api :as state-flow.api]
            [flowjure.server :as server]
            [state-flow.core :refer [flow]]))

(defn- do-request [service-fn verb route body headers]
  (let [headers-with-default (merge {"Content-Type" "application/json"} headers)
        encoded-body (json/encode body)]
    (pedestal.test/response-for service-fn verb route :headers headers-with-default :body encoded-body)))

(defn- parsed-response
  [{:keys [headers body] :as request}]
  (if (string/includes? (get headers "Content-Type") "application/json")
    (assoc request :body (json/decode body true))
    request))

(defn request!
  [{:keys [method uri body headers]}]
  (flow "makes HTTP request using pedestal test"
        [service-fn (state-flow.api/get-state (comp :io.pedestal.http/service-fn :server :server))]
        (-> service-fn
            (do-request method uri body headers)
            parsed-response
            state-flow.api/return)))

(defn start-system!
  ([]
   (server/run-test)))

(defn stop-system!
  [system]
  (component/stop-system system))