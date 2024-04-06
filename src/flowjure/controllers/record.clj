(ns flowjure.controllers.record
  (:require
   [flowjure.db.flow :as db.flow]
   [flowjure.db.record :as db.record]
   [flowjure.logic.record :as logic.record]
   [flowjure.models.in.record :as in.record]
   [java-time.api :as t]
   [schema.core :as s]))

(s/defn insert!
  [id :- s/Uuid
   record :- in.record/Record
   database]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id record) database)]
    (-> id
        (logic.record/->begin-complete-record record flow (t/instant))
        (db.record/insert-flow! database))
    :non-existing-flow-id))