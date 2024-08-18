(ns flowjure.models.db.record
  (:require
    [schema.core :as s]))

(s/defschema Path {(s/required-key :step-name) s/Str
                   (s/required-key :moved-at)  s/Inst})

(s/defschema Record {(s/required-key :_id)        s/Uuid
                     (s/optional-key :id)         s/Str
                     (s/required-key :flow-id)    s/Str
                     (s/required-key :details)    (s/pred map?)
                     (s/required-key :path)       [Path]
                     (s/required-key :created-at) s/Inst})