(ns flowjure.controllers.record
  (:require
    [flowjure.db.flow :as db.flow]
    [flowjure.db.record :as db.record]
    [flowjure.logic.helpers :as logic.helpers]
    [flowjure.logic.record :as logic.record]
    [flowjure.models.db.record :as models.db.record]
    [flowjure.models.in.record :as in.record]
    [flowjure.protocols.database :as protocols.database]
    [flowjure.time :as t]
    [schema.core :as s]))

(s/defn insert!
  [id :- s/Uuid
   record :- in.record/Record
   database :- protocols.database/IDatabase]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id record) database)]
    (-> id
        (logic.record/->begin-complete-record record flow (t/instant))
        (db.record/insert-record! database))
    :non-existing-flow-id))

(s/defn retrieve-by-id! :- models.db.record/Record
  [id :- s/Str
   database :- protocols.database/IDatabase]
  (when-let [result (db.record/retrieve-by-id! id database)]
    (logic.helpers/replace-mongo-id result id)))

(s/defn move!
  [id :- s/Str
   move-record :- in.record/MoveRecord
   database :- protocols.database/IDatabase]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id move-record) database)]
    (if-let [record (db.record/retrieve-by-id! id database)]
      (logic.record/old->next record flow move-record (t/instant))
      :non-existing-record-id)
    :non-existing-flow-id))