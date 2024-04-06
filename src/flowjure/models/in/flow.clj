(ns flowjure.models.in.flow
  (:require
   [schema.core :as s]))

(s/defschema Tag (s/enum :begin :end))

(s/defschema Step {(s/required-key :name) s/Str
                   (s/optional-key :next) [s/Str]
                   (s/optional-key :previous) [s/Str]
                   (s/optional-key :tag) Tag})

(s/defschema Flow {(s/required-key :name)        s/Str
                   (s/required-key :description) s/Str
                   (s/required-key :steps)       [Step]})
