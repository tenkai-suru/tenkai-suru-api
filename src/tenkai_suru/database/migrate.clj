(ns tenkai-suru.database.migrate
  (:require
    [tenkai-suru.database.core       :refer [migrations migrate-all]]
    [tenkai-suru.utility.config      :refer [reader]]
    [tenkai-suru.utility.environment :refer [environment]]
    [tenkai-suru.utility.log         :refer [fatal]]))


(defn database-connection-uri [environment]
  (if-let [database-connection-uri (-> (reader "database") environment :database-uri)]
    database-connection-uri
    (println ":database-uri not found for the given environment:" environment)))

(defn -main [& args]
  (if-let [environment (first args)]
    (if-let [database-uri (database-connection-uri (keyword environment))]
      (migrate-all database-uri (migrations "migrations")))
    (println "You must pass in the environment you wish to migrate. Example: lein migrate dev")))
