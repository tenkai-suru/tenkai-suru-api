(ns tenkai-suru.utility.environment-spec
  (:require
    [tenkai-suru.utility.environment :refer :all]
    [speclj.core :refer :all]))


(describe "environment"
  (it "defaults to the 'unconfigured' environment"
    (should= :unconfigured @-env))

  (it "throws an exception if the environment hasn't been configured yet"
    (should-throw clojure.lang.ExceptionInfo "Environment hasn't been configured yet."
      (environment)))

  (it "gets the current-environment"
    (reset! -env :some-other-env)
    (should= :some-other-env (environment))))
