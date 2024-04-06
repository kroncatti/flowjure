(ns flowjure.logic.record
  (:require
   [flowjure.logic.flow :as logic.flow]
   [flowjure.models.db.record :as models.db.record]
   [flowjure.models.in.flow :as in.flow]
   [flowjure.models.in.record :as in.record]
   [schema.core :as s]))

(s/defn first-step-history :- models.db.record/Path
  [flow :- in.flow/Flow
   instant :- s/Inst]
  {:step-name (logic.flow/find-begin-step-name flow)
   :moved-at  instant})

(s/defn ->begin-complete-record :- models.db.record/Record
  [id :- s/Uuid
   record :- in.record/Record
   flow :- in.flow/Flow
   instant :- s/Inst]
  (merge record
         {:_id        id
          :path       [(first-step-history flow instant)]
          :created-at instant}))