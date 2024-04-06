(ns flowjure.time
  (:require
   [schema.core :as s])
  (:import
   [java.util Date]))

(s/defn instant :- s/Inst
  []
  (Date.))
