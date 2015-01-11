(ns tenkai-suru.database.migrations-spec
  (:require
    [clojure.java.jdbc :refer :all]
    [speclj.core :refer :all]
    [tenkai-suru.database.migrations :refer :all]))


(def db-spec {:subprotocol "h2"
              :classname   "org.h2.Driver"
              :subname     "mem"})

(describe "database.migrate"
  (let [migrations-directory "spec/support/migrations"
        migrations (sql-migrations migrations-directory)]

    (context "migrations"
      (it "lists the migrations from the given migrations directory"
        (should= "19931105000000-create-dunder-mifflin-employees-table"
                 (-> migrations first :id))))

    (context "migrate-all"
      (it "migrates the given database with the given migrations"
        (migrate-all "jdbc:h2:mem" migrations)
        (let [tables         (map :table_name (query db-spec "SHOW TABLES;"))
              table-exists? #(boolean (some #{%} tables))]
          (should= true (table-exists? "DUNDER_MIFFLIN_EMPLOYEES")))))

    (context "rollback"
      (it "rolls back the most recent migration in a given database"
        (rollback "jdbc:h2:mem" (last migrations))
        (let [tables         (map :table_name (query db-spec "SHOW TABLES;"))
              table-exists? #(boolean (some #{%} tables))]
          (should= false (table-exists? "DUNDER_MIFFLIN_EMPLOYEES")))))))
