(ns flowjure.db.record
  (:require
   [flowjure.models.db.record :as models.db.record]
   [monger.collection :as mc]
   [schema.core :as s]))

(s/defn insert-flow! :- models.db.record/Record
  [record :- models.db.record/Record
   database]
  (mc/insert-and-return database "record" record))