(ns flowjure.controllers.record
  (:require [schema.core :as s]
            [flowjure.models.in.record :as in.record]))


(s/defn insert-new!
  [record :- in.record/Record])