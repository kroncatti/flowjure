(ns components.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [interceptors.injection :as interceptors.inject]))

(defn test?
  [service-map]
  (= :test (:env service-map)))

; Reloadable routes: https://github.com/pedestal/pedestal/blob/master/service-template/src/leiningen/new/pedestal_service/server.clj#L21

(defrecord Pedestal [service-map service app]
  component/Lifecycle
  (start [this]
    (let [service-map+interceptors (-> service-map
                                       http/default-interceptors
                                       (update ::http/interceptors conj (interceptors.injection/injection app)))]
      (if service
        this
        (assoc this :service (if (not (test? service-map))
                               (-> service-map+interceptors
                                   http/create-server
                                   http/start)
                               (-> service-map+interceptors
                                   (http/create-server service-map)))))))
  (stop [this]
    (when (and service (not (test? service-map)))
      (http/stop service))
    (assoc this :service nil)))

(defn new-pedestal
  []
  (component/using (map->Pedestal {}) [:app]))
