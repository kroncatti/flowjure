(ns flowjure.components.mongo
  (:require
    [com.stuartsierra.component :as component]
    [flowjure.protocols.database :as protocols.database]
    [monger.core :as mg]
    [monger.collection :as mc]))

(defrecord Database [uri]
  component/Lifecycle

  (start [this]
    (let [{:keys [conn db]} (mg/connect-via-uri uri)]
      (assoc this
        :database db
        ::connection conn)))

  (stop [this]
    (-> this ::connection mg/disconnect)
    this)

  protocols.database/Database
  (insert-and-return! [this coll m]
    (mc/insert-and-return (:database this) coll m))

  (find-one-by-key! [this coll m]
    (mc/find-one-as-map (:database this) coll m)))

(defn new-mongo-db [uri]
  (map->Database {:uri uri}))

(defrecord DatabaseMock []
  component/Lifecycle

  (start [this]
    (assoc this :database (atom {})))

  (stop [this]
    (dissoc this :database))

  protocols.database/Database
  (insert-and-return! [this coll m]
    (swap! (:database this) update coll conj m))

  (find-one-by-key! [this coll m]
    (when-let [local (get @(:database this) coll)]
      (let [key (first (keys m))
            target-val (get m key)]
        (first (filter (comp #{target-val} key) local))))))

(defn new-mongo-db-mock []
  (map->DatabaseMock {}))