(ns flowjure.unit.logic.flow_test
  (:require [clojure.test :refer [deftest is testing use-fixtures]]
            [flowjure.logic.flow :as logic.flow]
            [matcher-combinators.test :refer [match?]]
            [schema.test]))

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
                     :tag :end}]}]
 (deftest find-tag-and-return-name
   (testing "Testing for begin"
     (is (match? "start"
                 (logic.flow/find-begin-name flow))))

   (testing "Testing for end"
     (is (match? "end"
                 (logic.flow/find-end-name flow))))))