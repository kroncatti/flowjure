(ns flowjure.controllers.flow
  (:require
   [flowjure.db.flow :as db.flow]
   [flowjure.logic.helpers :as logic.helpers]
   [flowjure.models.in.flow :as in.flow]
   [schema.core :as s]))

(s/defn insert!
  [id :- s/Uuid
   flow :- in.flow/Flow
   database]
  (-> flow
      (logic.helpers/set-mongo-id id)
      (db.flow/insert-flow! database)))

(s/defn retrieve-by-id!
  [id :- s/Str
   database]
  (when-let [result (db.flow/retrieve-by-id! id database)]
    (logic.helpers/replace-mongo-id result id)))