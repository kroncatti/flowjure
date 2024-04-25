(ns flowjure.server
  (:require
   [com.stuartsierra.component :as component]
   [flowjure.components :as components]
   [schema.core :as s]))

(def system-atom (atom nil))

(defn start-system! [system-map]
  (->> system-map
       component/start
       (reset! system-atom)))

(defn stop-system! []
  (swap! system-atom (fn [system] (when system (component/stop system)))))

(s/defn build-conn-url :- s/Str
  [username :- s/Str
   password :- s/Str
   db-name :- s/Str]
  (str "mongodb://" username ":" password "@" "127.0.0.1:27017/" db-name "?authSource=admin"))

(defn -main [& args]
  (-> {:env    :prod
       :port   8890
       :db-url (build-conn-url (System/getenv "DB_USERNAME")
                               (System/getenv "DB_PASSWORD")
                               (System/getenv "DB_NAME"))}
      components/new-system
      start-system!))

(defn run-dev [& args]
  (-> {:env    :dev
       :port   8891
       :db-url (build-conn-url (System/getenv "DB_USERNAME")
                               (System/getenv "DB_PASSWORD")
                               (System/getenv "DB_NAME"))}
      components/new-system
      start-system!))

(defn run-test [& args]
  (-> {:env  :test
       :port 8892}
      components/new-test-system
      start-system!))

(comment
  (-main)
  (stop-system!))

(comment
  (require '[com.stuartsierra.component.repl :refer [reset set-init start stop system]])
  (set-init (fn [old-system] (-main)))
  (require '[com.stuartsierra.component.repl :as crepl])
  (crepl/reset))