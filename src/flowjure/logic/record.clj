(ns flowjure.logic.record
  (:require
    [flowjure.logic.flow :as logic.flow]
    [flowjure.logic.helpers :as logic.helpers]
    [flowjure.models.db.flow :as models.db.flow]
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
  (-> record
      (merge {:path       [(first-step-history flow instant)]
              :created-at instant})
      (logic.helpers/set-mongo-id id)))

(s/defn find-current-step :- s/Str
  [record :- models.db.record/Record]
  (->> record
       :path
       (sort-by :moved-at)
       last
       :step-name))

(s/defn can-move?
  [record :- models.db.record/Record
   flow :- models.db.flow/Flow
   move-record-to :- in.record/MoveRecord]
  ())