(ns flowjure.controllers.flow
  (:require
    [flowjure.db.flow :as db.flow]
    [flowjure.logic.helpers :as logic.helpers]
    [flowjure.models.db.flow :as models.db.flow]
    [flowjure.models.in.flow :as in.flow]
    [flowjure.protocols.database :as protocols.database]
    [flowjure.time :as t]
    [schema.core :as s]))

(s/defn insert!
  [id :- s/Uuid
   flow :- in.flow/Flow
   database :- protocols.database/IDatabase]
  (-> flow
      (logic.helpers/set-mongo-id id)
      (assoc :created-at (t/instant))
      (db.flow/insert-flow! database)))

(s/defn retrieve-by-id! :- models.db.flow/Flow
  [id :- s/Str
   database :- protocols.database/IDatabase]
  (when-let [result (db.flow/retrieve-by-id! id database)]
    (logic.helpers/replace-mongo-id result id)))