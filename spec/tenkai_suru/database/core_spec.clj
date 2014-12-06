(ns tenkai-suru.database.core-spec
  (:require
    [speclj.core               :refer :all]
    [tenkai-suru.database.core :refer :all]
    [clojure.java.jdbc         :refer :all]))


(def db-spec {:subprotocol "h2"
              :classname   "org.h2.Driver"
              :subname     "mem"})

(describe "database.core"
  (let [migrations-directory "spec/support/migrations"
        migrations (migrations migrations-directory)]

    (context "migrations"
      (it "lists the migrations from the given migrations directory"
        (should= "19931105000000-create-dunder-mifflin-employees-table"
                 (-> migrations first :id))))

    (context "migrate-all"
      (it "migrates the given database with the given migrations"
        (migrate-all "jdbc:h2:mem" migrations)
        (let [tables         (map :table_name (query db-spec "SHOW TABLES;"))
              table-exists? #(boolean (some #{%} tables))]
          (should= true (table-exists? "DUNDER_MIFFLIN_EMPLOYEES")))))))
