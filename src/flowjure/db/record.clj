(ns flowjure.db.record
  (:require
   [flowjure.models.db.record :as models.db.record]
   [monger.collection :as mc]
   [schema.core :as s]))

(s/defn retrieve-by-id! :- models.db.record/Record
  [uuid :- s/Str
   database]
  (mc/find-one-as-map database "record" {:_id (parse-uuid uuid)}))

(s/defn insert-record! :- models.db.record/Record
  [record :- models.db.record/Record
   database]
  (mc/insert-and-return database "record" record))