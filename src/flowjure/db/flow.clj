(ns flowjure.db.flow
  (:require
   [flowjure.models.db.flow :as models.db.flow]
   [flowjure.protocols.database :as protocols.database]
   [schema.core :as s]))

(s/defn retrieve-by-id! :- models.db.flow/Flow
  [uuid :- s/Str
   database]
  (protocols.database/find-one-by-key! database "flow" {:_id (parse-uuid uuid)}))

(s/defn insert-flow! :- models.db.flow/Flow
  [flow :- models.db.flow/Flow
   database]
  (protocols.database/insert-and-return! database "flow" flow))