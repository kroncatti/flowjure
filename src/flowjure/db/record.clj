(ns flowjure.db.record
  (:require [schema.core :as s]
            [monger.collection :as mc]
            [flowjure.models.db.record :as models.db.record]))

(s/defn insert-flow! :- models.db.record/Record
  [record :- models.db.record/Record
   database]
  (mc/insert-and-return database :record record))