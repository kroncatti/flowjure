(ns unit.flowjure.components.mongo
  (:require
   [com.stuartsierra.component :as component]
   [flowjure.components :as components]
   [schema.test]))

(def test-db (:db (component/start (components/new-test-system))))

(flowjure.protocols.database/insert-and-return! test-db "something" {:my "map"
                                                                     :id "12345"})

(flowjure.protocols.database/find-one-by-key! test-db "something" {:my "map"
                                                                   :id "12345"})