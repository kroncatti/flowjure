(ns flowjure.logic.helpers
  (:require
   [schema.core :as s]))

(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
            (if (and (map? v1) (map? v2))
              (merge-with deep-merge v1 v2)
              v2))]
    (if (some identity vs)
      (reduce #(rec-merge %1 %2) v vs)
      (last vs))))

(s/defn set-mongo-id :- (s/pred map?)
  [m :- (s/pred map?)
   id :- s/Uuid]
  (assoc m :_id id :id (str id)))

(s/defn replace-mongo-id :- (s/pred map?)
  [m :- (s/pred map?)
   id :- s/Str]
  (-> m
      (dissoc :_id)
      (assoc :id id)))