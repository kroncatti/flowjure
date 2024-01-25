(ns components
  (:require [com.stuartsierra.component :as component]
            [components.pedestal :as components.pedestal]
            [app :as app]))


(defn new-app [env]
  (component/system-map
   :service-map (app/service-map env)
   :pedestal (component/using (components.pedestal/new-pedestal) [:service-map])))
