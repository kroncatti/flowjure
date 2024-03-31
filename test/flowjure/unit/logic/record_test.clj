(ns flowjure.unit.logic.record-test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [flowjure.logic.record :as logic.record]
            [matcher-combinators.test :refer [match?]]
            [schema.test]
            [java-time.api :as t]))

(use-fixtures :once schema.test/validate-schemas)

(let [flow {:name "my-flow"
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
      record {:flow-id    "22416b73-cadc-4353-b8f9-327fad9c0952"
              :details    {:my-specific-record "example"
                           :any-data-we-want   "here"}}
      ]
  (deftest build-complete-record

    (testing "Testing for end"
      (is (match? "end"
                  "")))))

#_ (->complete-record (random-uuid)
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