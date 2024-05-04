(ns integration.flowjure.flow-test
  (:require [integration.helpers.helpers :as helpers]
            [integration.helpers.mocks :as mocks]
            [state-flow.api :as flow :refer [defflow flow]]
            [state-flow.assertions.matcher-combinators :refer [match?]]))

(defflow post-flow-test
  {:init       helpers/start-system!
   :cleanup    helpers/stop-system!
   :fail-fast? true}
  (flow "Post new flow"
        (match? {:status 200
                 :body   {:result "success"
                          :id     string?}}
                (helpers/request! {:method  :post
                                   :uri     "api/flow"
                                   :headers {"Accept" "application/json"}
                                   :body    mocks/in-sample-flow}))))

(defflow get-flow-by-id-test
  {:init       helpers/start-system!
   :cleanup    helpers/stop-system!
   :fail-fast? true}
  (flow "Post new flow and retrieve it by id"
        [flow-id (flow/fmap (comp :id :body) (helpers/request! {:method  :post
                                                                :uri     "api/flow"
                                                                :headers {"Accept" "application/json"}
                                                                :body    mocks/in-sample-flow}))]
        (match? {:status 200
                 :body   (assoc mocks/in-sample-flow :id string?)}
                (helpers/request! {:method  :get
                                   :headers {"Accept" "application/json"}
                                   :uri     (str "api/flow/" flow-id)}))))