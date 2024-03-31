(ns flowjure.logic.flow
  (:require [schema.core :as s]
            [flowjure.models.in.flow :as in.flow]))

(s/defn find-step-name-for-tag :- s/Str
  [flow :- in.flow/Flow
   tag :- s/Keyword]
  (->> flow
       :steps
       (filterv #(= tag (:tag %)))
       first
       :name))

(s/defn find-begin-name :- s/Str
  [flow :- in.flow/Flow]
  (find-step-name-for-tag flow :begin))

(s/defn find-end-name :- s/Str
  [flow :- in.flow/Flow]
  (find-step-name-for-tag flow :end))

