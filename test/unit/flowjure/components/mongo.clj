(ns unit.flowjure.components.mongo
  (:require
    [clojure.test :refer [deftest is testing use-fixtures]]
    [flowjure.interceptors.coercer :as interceptors.coercer]
    [matcher-combinators.test :refer [match?]]
    [flowjure.components :as components]
    [com.stuartsierra.component :as component]
    [schema.core :as s]
    [schema.test]))


(def test-db (:db (component/start (components/new-test-system))))

(flowjure.protocols.database/insert-and-return! test-db "something" {:my "map"
                                                                     :id "12345"})

(flowjure.protocols.database/find-one-by-key! test-db "something" {:my "map"
                                                                   :id "12345"})