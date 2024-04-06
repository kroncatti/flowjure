(ns flowjure.controllers.record
  (:require
   [flowjure.db.flow :as db.flow]
   [flowjure.db.record :as db.record]
   [flowjure.logic.helpers :as logic.helpers]
   [flowjure.logic.record :as logic.record]
   [flowjure.models.db.record :as models.db.record]
   [flowjure.models.in.record :as in.record]
   [flowjure.time :as t]
   [schema.core :as s]))

(s/defn insert!
  [id :- s/Uuid
   record :- in.record/Record
   database]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id record) database)]
    (-> id
        (logic.record/->begin-complete-record record flow (t/instant))
        (db.record/insert-record! database))
    :non-existing-flow-id))

(s/defn retrieve-by-id! :- models.db.record/Record
  [id :- s/Str
   database]
  (when-let [result (db.record/retrieve-by-id! id database)]
    (logic.helpers/replace-mongo-id result id)))