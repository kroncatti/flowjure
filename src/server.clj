(ns server
  (:require [com.stuartsierra.component :as component]
            [components :as components]))


(defn -main []
  (component/start (components/new-system {:env  :prod
                                           :port 8890})))

(defn run-dev []
  (component/start (components/new-system {:env  :dev
                                           :port 8891})))

(defn run-test []
  (component/start (components/new-system {:env  :test
                                           :port 8892})))
