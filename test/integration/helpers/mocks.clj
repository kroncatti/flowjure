(ns integration.helpers.mocks)

(def in-sample-flow {:name        "my-flow"
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

(def in-sample-record {:flow-id (random-uuid)
                       :details {:my-specific-record "example"
                                 :any-data-we-want   "here"}})