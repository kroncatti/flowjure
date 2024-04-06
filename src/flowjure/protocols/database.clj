(ns flowjure.protocols.database
  (:require [schema.core :as s]))


(defprotocol Database
  "Interface for Database"
  (find-one-by-key! [database coll map])
  (insert-and-return! [database coll map]))

(def IDatabase (s/protocol Database))