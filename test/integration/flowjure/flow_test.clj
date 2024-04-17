(ns integration.flowjure.flow-test
  (:require [integration.helpers :as helpers]
            [state-flow.api :refer [defflow flow]]
            [state-flow.assertions.matcher-combinators :refer [match?]]))


(defflow health-check
         {:init       helpers/start-system!
          :cleanup    helpers/stop-system!
          :fail-fast? true}
         (flow "Should ping health-check"
               (match? {:status 200
                        :body   "pong!"}
                       (helpers/request! {:method :get
                                          :uri    "api/admin/health"}))))