(ns flowjure.db.flow
  (:require [schema.core :as s]
            [monger.collection :as mc]
            [flowjure.models.db.flow :as models.db.flow]))


(s/defn retrieve-by-id! :- models.db.flow/Flow
  [uuid :- s/Str
   database]
  (mc/find-one-as-map database :flow {:_id (parse-uuid uuid)}))

(s/defn insert-flow! :- models.db.flow/Flow
  [flow :- models.db.flow/Flow
   database]
  (mc/insert-and-return database :flow flow))