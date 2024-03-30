(ns flowjure.models.in.record
  (:require [schema.core :as s]))

(s/defschema Record {(s/required-key :flow-id) s/Str
                     (s/required-key :details) (s/pred map?)})