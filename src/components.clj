(ns components
  (:require [com.stuartsierra.component :as component]
            [components.app :as components.app]
            [components.pedestal :as components.pedestal]
            [components.mongo :as components.mongo]
            [app :as app]))

(def visible
  [:db :pedestal :service-map])


(defn new-system [& options]
  (let [{:keys [env port db-url]} options]
    (component/system-map
     :service-map (app/service-map env port)
     :db (components.mongo/new-mongo-db db-url)
     :pedestal (component/using (components.pedestal/new-pedestal) [:service-map :db])
     :app (component/using (components.app/new-app) visible))))
