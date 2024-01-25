(ns components
  (:require [com.stuartsierra.component :as component]
            [components.pedestal :as components.pedestal]
            [app :as app]))


(defn new-system [& options]
  (let [{:keys [env port]} options]
    (component/system-map
     :service-map (app/service-map env port)
     :pedestal (component/using (components.pedestal/new-pedestal) [:service-map]))))
