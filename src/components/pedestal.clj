(ns components.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [interceptors.injection :as interceptors.injection]))


(defn env?
  [service-map env]
  (= env (:env service-map)))

; Reloadable routes: https://github.com/pedestal/pedestal/blob/master/service-template/src/leiningen/new/pedestal_service/server.clj#L21
(defn start-pedestal! [service-map app]
  (cond-> (-> service-map
              http/default-interceptors
              (update ::http/interceptors conj (interceptors.injection/injection app))
              http/create-server)
          (not (env? service-map :test)) http/start))

(defrecord Pedestal [service-map service app]
  component/Lifecycle
  (start [this]
    (if service
      this
      (assoc this :service (start-pedestal! service-map app))))
  (stop [this]
    (when (and service (not (env? service-map :test)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal
  []
  (component/using (map->Pedestal {}) [:app]))
