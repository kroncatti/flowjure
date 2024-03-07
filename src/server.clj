(ns server
  (:require [com.stuartsierra.component :as component]
            [components :as components]))

(defn -main [& args]
  (component/start (components/new-system {:env  :prod
                                           :port 8890
                                           :db-url  "mongodb://user:password@127.0.0.1:27017/test"})))

(defn run-dev [& args]
  (component/start (components/new-system {:env  :dev
                                           :port 8891
                                           :db-url  "mongodb://user:password@127.0.0.1:27017/test"})))

(defn run-test [& args]
  (component/start (components/new-system {:env  :test
                                           :port 8892
                                           :db-url  "mongodb://user:password@127.0.0.1:27017/test"})))
