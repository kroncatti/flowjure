(ns flowjure.logic.flow
  (:require
   [flowjure.models.in.flow :as in.flow]
   [schema.core :as s]))

(s/defn find-step-name-for-tag :- s/Str
  [flow :- in.flow/Flow
   tag :- s/Keyword]
  (->> flow
       :steps
       (filterv #(= tag (:tag %)))
       first
       :name))

(s/defn find-begin-step-name :- s/Str
  [flow :- in.flow/Flow]
  (find-step-name-for-tag flow :begin))

(s/defn find-end-step-name :- s/Str
  [flow :- in.flow/Flow]
  (find-step-name-for-tag flow :end))

