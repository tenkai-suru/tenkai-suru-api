(ns tenkai-suru.database.core-spec
  (:require
    [speclj.core               :refer :all]
    [tenkai-suru.database.core :refer :all]))


(describe "database.core"
  (context "database-connection-uri"
    (it "returns the given environment's :database-uri from the database config file"
      (should= "jdbc:postgresql://localhost:5432/scranton-branch"
               (database-connection-uri :test)))

    (it "returns nil if the given environment's :database-uri cannot be found"
      (should-be-nil (database-connection-uri :not-an-env)))))
