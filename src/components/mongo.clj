(ns components.mongo
  (:require [com.stuartsierra.component :as component]
            [monger.core :as mg]))


(defrecord Database [uri database connection]
  component/Lifecycle

  (start [component]
    (let [{:keys [conn db]} (mg/connect-via-uri uri)]
      (assoc component :database db
                       ::connection conn)))

  (stop [component]
    (-> component ::connection mg/disconnect)
    component))

(defn new-mongo-db [uri]
  (map->Database {:uri uri}))
