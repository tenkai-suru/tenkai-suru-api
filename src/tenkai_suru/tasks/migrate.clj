(ns tenkai-suru.tasks.migrate
  (:require
    [tenkai-suru.database.core :refer [database-connection-uri]]
    [tenkai-suru.database.migrations :refer [migrate-all migrations]]
    [tenkai-suru.utility.environment :refer [environment]]
    [tenkai-suru.utility.log :refer [error]]))


(defn execute []
  (if-let [database-uri (database-connection-uri (environment))]
    (migrate-all database-uri (migrations "resources/migrations"))
    (error (str "database-uri not found for the given environment '" environment "'"))))
