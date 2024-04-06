(ns unit.flowjure.interceptors.coercer-test
  (:require
   [clojure.test :refer [deftest is testing use-fixtures]]
   [flowjure.interceptors.coercer :as interceptors.coercer]
   [matcher-combinators.test :refer [match?]]
   [schema.core :as s]
   [schema.test]))

(use-fixtures :once schema.test/validate-schemas)

(s/defschema SampleModel {(s/required-key :name) s/Str})

(deftest coerce-body-on-enter-test
  (testing "Testing vanilla case with no body"
    (is (= {:my-map "hey"}
           (interceptors.coercer/coerce-body-on-enter {:my-map "hey"} nil))))

  (testing "Testing scenario with possible body validation for json-params"
    (is (= {:request {:json-params {:name ""}
                      :data        {:name ""}}}
           (interceptors.coercer/coerce-body-on-enter {:request {:json-params {:name ""}}} SampleModel))))

  (testing "Testing scenario with possible body validation for edn-params"
    (is (= {:request {:edn-params {:name ""}
                      :data       {:name ""}}}
           (interceptors.coercer/coerce-body-on-enter {:request {:edn-params {:name ""}}} SampleModel))))

  (testing "Testing failure on validation"
    (is (match? {:type  :schema.core/error
                 :error {:name 'missing-required-key
                         :test 'disallowed-key}}
                (try
                  (interceptors.coercer/coerce-body-on-enter {:request {:edn-params {:test ""}}} SampleModel)
                  (catch Exception ex
                    (ex-data ex)))))))