(ns flowjure.db.flow
  (:require
   [flowjure.models.db.flow :as models.db.flow]
   [monger.collection :as mc]
   [schema.core :as s]))

(s/defn retrieve-by-id! :- models.db.flow/Flow
  [uuid :- s/Str
   database]
  (mc/find-one-as-map database "flow" {:_id (parse-uuid uuid)}))

(s/defn insert-flow! :- models.db.flow/Flow
  [flow :- models.db.flow/Flow
   database]
  (mc/insert-and-return database "flow" flow))