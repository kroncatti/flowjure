(ns flowjure.components
  (:require [com.stuartsierra.component :as component]
            [flowjure.components.app :as components.app]
            [flowjure.components.pedestal :as components.pedestal]
            [flowjure.components.mongo :as components.mongo]
            [flowjure.app :as app]))

(def visible
  [:db])


(defn new-system [& options]
  (let [{:keys [env port db-url]} options]
    (component/system-map
     :service-map (app/service-map env port)
     :db (components.mongo/new-mongo-db db-url)
     :pedestal (component/using (components.pedestal/new-pedestal) [:service-map :app])
     :app (component/using (components.app/new-app) visible))))
