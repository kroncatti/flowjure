(ns flowjure.db.record
  (:require
   [flowjure.models.db.record :as models.db.record]
   [flowjure.protocols.database :as protocols.database]
   [schema.core :as s]))

(s/defn retrieve-by-id! :- models.db.record/Record
  [uuid :- s/Str
   database]
  (protocols.database/find-one-by-key! database "record" {:_id (parse-uuid uuid)}))

(s/defn insert-record! :- models.db.record/Record
  [record :- models.db.record/Record
   database]
  (protocols.database/insert-and-return! database "record" record))