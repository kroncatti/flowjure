(ns flowjure.models.db.flow
  (:require
   [schema.core :as s]))

(s/defschema Tag (s/enum "begin" "end"))

(s/defschema Step {(s/required-key :name)     s/Str
                   (s/optional-key :next)     [s/Str]
                   (s/optional-key :previous) [s/Str]
                   (s/optional-key :tag)      Tag})

(s/defschema Flow {(s/required-key :_id)         s/Uuid
                   (s/optional-key :id)          s/Str
                   (s/required-key :name)        s/Str
                   (s/required-key :description) s/Str
                   (s/required-key :steps)       [Step]
                   (s/required-key :created-at)  s/Inst})
