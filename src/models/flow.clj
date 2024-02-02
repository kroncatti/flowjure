(ns models.flow
  (:require [schema.core :as s]))


(s/defschema Flow {(s/required-key :name)        s/Str
                   (s/required-key :description) s/Str
                   (s/required-key :begin-at)    s/Keyword
                   (s/required-key :finish-at)   s/Keyword
                   (s/required-key :steps)       [s/Any]})
