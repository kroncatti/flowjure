(ns flowjure.controllers.record
  (:require [schema.core :as s]
            [flowjure.models.in.record :as in.record]
            [flowjure.db.flow :as db.flow]
            [flowjure.logic.helpers :as logic.helpers]
            [flowjure.logic.record :as logic.record]
            [java-time.api :as t]))


(s/defn insert!
  [id :- s/Uuid
   record :- in.record/Record
   database]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id record) database)]
    (do
      (logic.record/->complete-record id record flow (t/instant)))
    :non-existing-flow-id))