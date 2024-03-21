(ns flowjure.server
  (:require [com.stuartsierra.component :as component]
            [flowjure.components :as components]
            [schema.core :as s]))

(s/defn build-conn-url :- s/Str
  [username :- s/Str
   password :- s/Str
   db-name :- s/Str]
  (str "mongodb://" username ":" password "@" "127.0.0.1:27017/" db-name "?authSource=admin"))

(defn -main [& args]
  (component/start (components/new-system {:env    :prod
                                           :port   8890
                                           :db-url (build-conn-url (System/getenv "DB_USERNAME")
                                                                   (System/getenv "DB_PASSWORD")
                                                                   (System/getenv "DB_NAME"))})))

(defn run-dev [& args]
  (component/start (components/new-system {:env    :dev
                                           :port   8891
                                           :db-url (build-conn-url (System/getenv "DB_USERNAME")
                                                                   (System/getenv "DB_PASSWORD")
                                                                   (System/getenv "DB_NAME"))})))

(defn run-test [& args]
  (component/start (components/new-system {:env    :test
                                           :port   8892
                                           :db-url (build-conn-url (System/getenv "DB_USERNAME")
                                                                   (System/getenv "DB_PASSWORD")
                                                                   (System/getenv "DB_NAME"))})))




;(require '[com.stuartsierra.component.repl :refer [reset set-init start stop system]])
;(set-init (fn [old-system] (-main)))
;(require '[com.stuartsierra.component.repl :as crepl])

