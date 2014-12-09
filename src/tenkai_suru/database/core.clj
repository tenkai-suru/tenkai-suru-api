(ns tenkai-suru.database.core
  (:require
    [tenkai-suru.utility.config :refer [reader]]))


(defn database-connection-uri [environment]
  (-> (reader "database") environment :database-uri))
