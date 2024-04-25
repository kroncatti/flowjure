(ns integration.flowjure.flow-test
  (:require [integration.helpers.helpers :as helpers]
            [state-flow.api :as flow :refer [defflow flow]]
            [state-flow.assertions.matcher-combinators :refer [match?]]))

(def sample-flow {:name        "my-flow"
                  :description "anything"
                  :steps       [{:name "start"
                                 :next ["step-1" "step-2" "step-3"]
                                 :tag  "begin"}
                                {:name     "step-1"
                                 :previous ["start"]
                                 :next     ["end"]}
                                {:name     "step-2"
                                 :previous ["start"]
                                 :next     ["step-4"]}
                                {:name     "step-3"
                                 :previous ["start"]
                                 :next     ["step-4"]}
                                {:name     "step-4"
                                 :previous ["step-3" "step-2"]
                                 :next     ["end"]}
                                {:name     "end"
                                 :previous ["step-4" "step-1"]
                                 :tag      "end"}]})

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
                                          :body    sample-flow}))))

(defflow get-flow-by-id-test
         {:init       helpers/start-system!
          :cleanup    helpers/stop-system!
          :fail-fast? true}
         (flow "Post new flow and retrieve it by id"
               [flow-id (flow/fmap (comp :id :body) (helpers/request! {:method  :post
                                                                       :uri     "api/flow"
                                                                       :headers {"Accept" "application/json"}
                                                                       :body    sample-flow}))]
               (match? {:status 200
                        :body   (assoc sample-flow :id string?)}
                       (helpers/request! {:method  :get
                                          :headers {"Accept" "application/json"}
                                          :uri     (str "api/flow/" flow-id)}))))