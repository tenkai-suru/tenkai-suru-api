(ns tenkai-suru.database.migrations
  (:require
    [ragtime.core :as rag]
    [ragtime.sql.files :as sql]))


(defn migrate-all [connection-uri resources-migrations]
  (rag/migrate-all
    (rag/connection connection-uri)
    resources-migrations))

(defn sql-migrations [migrations-dir]
  (sql/migrations migrations-dir))

(defn rollback [connection-uri migration]
  (rag/rollback
    (rag/connection connection-uri)
    migration))
