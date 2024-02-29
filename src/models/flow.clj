(ns models.flow
  (:require [schema.core :as s]))

(s/defschema Step {(s/required-key :name) s/Str
                   (s/optional-key :next) s/Str
                   (s/optional-key :previous) s/Keyword})

(s/defschema Flow {(s/required-key :name)        s/Str
                   (s/required-key :description) s/Str
                   (s/required-key :steps)       [Step]})
