(ns flowjure.components
  (:require
   [com.stuartsierra.component :as component]
   [flowjure.app :as app]
   [flowjure.components.app :as components.app]
   [flowjure.components.mongo :as components.mongo]
   [flowjure.components.pedestal :as components.pedestal]))

(def visible
  [:db])

(defn new-system [& options]
  (let [{:keys [env port db-url]} options]
    (component/system-map
     :service-map (app/service-map env port)
     :db (components.mongo/new-mongo-db db-url)
     :server (component/using (components.pedestal/new-pedestal) [:service-map :app])
     :app (component/using (components.app/new-app) visible))))

(defn new-test-system [& options]
  (let [{:keys [env port]} options]
    (component/system-map
     :service-map (app/service-map env port)
     :db (components.mongo/new-mongo-db-mock)
     :server (component/using (components.pedestal/new-pedestal) [:service-map :app])
     :app (component/using (components.app/new-app) visible))))
