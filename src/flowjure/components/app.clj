(ns flowjure.components.app
  (:require [com.stuartsierra.component :as component]))

(defrecord App []

  component/Lifecycle
  (start [this]
    this)
  (stop [this]
    this))

(defn new-app [] (map->App {}))
