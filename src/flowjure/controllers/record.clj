(ns flowjure.controllers.record
  (:require [schema.core :as s]
            [flowjure.models.in.record :as in.record]
            [flowjure.db.flow :as db.flow]
            [flowjure.logic.helpers :as logic.helpers]))


(s/defn insert!
  [id :- s/Uuid
   record :- in.record/Record
   database]
  (if-let [flow (db.flow/retrieve-by-id! (:flow-id record) database)]
    (do

      )
    :non-existing-flow-id))