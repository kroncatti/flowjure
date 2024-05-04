(ns integration.flowjure.record-test
  (:require [integration.helpers.helpers :as helpers]
            [integration.helpers.mocks :as mocks]
            [state-flow.api :as flow :refer [defflow flow]]
            [state-flow.assertions.matcher-combinators :refer [match?]]))

(defflow post-record-non-existing-flow-test
  {:init       helpers/start-system!
   :cleanup    helpers/stop-system!
   :fail-fast? true}
  (flow "Post new record for non-existing flow"
        (match? {:status 400
                 :body   {:details
                          {:type  "invalid-flow-id"
                           :error "flow-id was not found in database"}}}
                (helpers/request! {:method  :post
                                   :uri     "api/record"
                                   :headers {"Accept" "application/json"}
                                   :body    mocks/in-sample-record}))))

(defflow post-record-existing-flow-test
  {:init       helpers/start-system!
   :cleanup    helpers/stop-system!
   :fail-fast? true}
  (flow "Post new flow and post record linked to it"
        [flow-id (flow/fmap (comp :id :body) (helpers/request! {:method  :post
                                                                :uri     "api/flow"
                                                                :headers {"Accept" "application/json"}
                                                                :body    mocks/in-sample-flow}))]
        (match? {:status 200
                 :body   {:result "success"
                          :id     string?}}
                (helpers/request! {:method  :post
                                   :headers {"Accept" "application/json"}
                                   :uri     "api/record"
                                   :body    (assoc mocks/in-sample-record :flow-id flow-id)}))))

(defflow retrieve-record-test
  {:init       helpers/start-system!
   :cleanup    helpers/stop-system!
   :fail-fast? true}
  (flow "Post new flow, post record linked to it and retrieve record back"
        [flow-id (flow/fmap (comp :id :body) (helpers/request! {:method  :post
                                                                :uri     "api/flow"
                                                                :headers {"Accept" "application/json"}
                                                                :body    mocks/in-sample-flow}))
         record-id (flow/fmap (comp :id :body) (helpers/request! {:method  :post
                                                                  :headers {"Accept" "application/json"}
                                                                  :uri     "api/record"
                                                                  :body    (assoc mocks/in-sample-record :flow-id flow-id)}))]
        (match? {:status 200
                 :body   {:flow-id flow-id
                          :id      record-id
                          :details {:my-specific-record "example"
                                    :any-data-we-want   "here"}
                          :path    [{:step-name "start", :moved-at string?}]}}
                (helpers/request! {:method  :get
                                   :headers {"Accept" "application/json"}
                                   :uri     (str "api/record/" record-id)}))))