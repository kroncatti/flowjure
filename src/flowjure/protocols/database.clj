(ns flowjure.protocols.database)


(defprotocol Database
  "Interface for Database"
  (find-one-by-key! [database coll map])
  (insert-and-return! [database coll map]))