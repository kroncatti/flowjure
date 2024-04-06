(ns flowjure.components.mongo
  (:require
    [com.stuartsierra.component :as component]
    [monger.core :as mg]))

(defrecord Database [uri]
  component/Lifecycle

  (start [this]
    (let [{:keys [conn db]} (mg/connect-via-uri uri)]
      (assoc this
        :database db
        ::connection conn)))

  (stop [this]
    (-> this ::connection mg/disconnect)
    this))

(defn new-mongo-db [uri]
  (map->Database {:uri uri}))
