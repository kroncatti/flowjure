(ns flowjure.logic.helpers
  (:require
   [schema.core :as s]))

(s/defn set-mongo-id :- (s/pred map?)
  [m :- (s/pred map?)
   id :- s/Uuid]
  (assoc m :_id id))

(s/defn replace-mongo-id :- (s/pred map?)
  [m :- (s/pred map?)
   id :- s/Str]
  (-> m
      (dissoc :_id)
      (assoc :id id)))