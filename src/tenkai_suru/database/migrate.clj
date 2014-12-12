(ns tenkai-suru.database.migrate
  (:require
    [environ.core              :refer [env]]
    [ragtime.core              :as    rag]
    [ragtime.sql.files         :as    sql]
    [tenkai-suru.database.core :refer [database-connection-uri]]))


(defn migrate-all [connection-uri migrations]
  (rag/migrate-all
    (rag/connection connection-uri)
    migrations))

(defn migrations [migrations-dir]
  (sql/migrations migrations-dir))

(defn -main []
  (if-let [environment (env :env)]
    (if-let [database-uri (database-connection-uri environment)]
      (migrate-all database-uri (migrations "migrations"))
      (println ":database-uri not found for the given environment:" environment))
    (println "You must pass in the environment you wish to migrate as a profile. Example: lein with-profile development migrate")))
