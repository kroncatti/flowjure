(ns flowjure.logic.record
  (:require [schema.core :as s]
            [flowjure.logic.flow :as logic.flow]
            [flowjure.models.in.flow :as in.flow]
            [flowjure.models.in.record :as in.record]
            [flowjure.models.db.record :as models.db.record]
            [java-time.api :as t]))


(s/defn first-step-history :- models.db.record/Path
  [flow :- in.flow/Flow
   t :- s/Inst]
  {:step-name (logic.flow/find-begin-name flow)
   :moved-at  t})

(s/defn ->complete-record :- models.db.record/Record
  [id :- s/Uuid
   record :- in.record/Record
   flow :- in.flow/Flow
   t :- s/Inst]
  (merge record
         {:_id        id
          :path       [(first-step-history flow t)]
          :created-at t}))

(->complete-record (random-uuid)
                   {:flow-id    "22416b73-cadc-4353-b8f9-327fad9c0952"
                    :details    {:my-specific-record "example"
                                 :any-data-we-want   "here"}}
                   {:name "my-flow"
                    :description "anything"
                    :steps [{:name "start"
                             :next ["step-1" "step-2" "step-3"]
                             :tag :begin}
                            {:name "step-1"
                             :previous ["start"]
                             :next ["end"]}
                            {:name "step-2"
                             :previous ["start"]
                             :next ["step-4"]}
                            {:name "step-3"
                             :previous ["start"]
                             :next ["step-4"]}
                            {:name "step-4"
                             :previous ["step-3" "step-2"]
                             :next ["end"]}
                            {:name "end"
                             :previous ["step-4" "step-1"]
                             :tag :end}]}
                   (t/instant))