(ns tenkai-suru.database.migrate
  (:require
    [ragtime.core              :as    rag]
    [ragtime.sql.files         :as    sql]
    [tenkai-suru.database.core :refer [database-connection-uri]]))


(defn migrate-all [connection-uri migrations]
  (rag/migrate-all
    (rag/connection connection-uri)
    migrations))

(defn migrations [migrations-dir]
  (sql/migrations migrations-dir))

(defn -main [& args]
  (if-let [environment (first args)]
    (if-let [database-uri (database-connection-uri (keyword environment))]
      (migrate-all database-uri (migrations "migrations"))
      (println ":database-uri not found for the given environment:" environment))
    (println "You must pass in the environment you wish to migrate. Example: lein migrate development")))
