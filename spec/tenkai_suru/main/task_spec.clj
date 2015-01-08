(ns tenkai-suru.main.task-spec
  (:require
    [speclj.core :refer :all]
    [tenkai-suru.utility.environment :refer [-env environment]]
    [tenkai-suru.main.task :refer :all]
    [tenkai-suru.spec-helper :refer [mock-fn silence-logger!]]
    [tenkai-suru.utility.log :refer [error info]]))


(describe "tasks"
  (before (silence-logger!))
  (around [it]
    (let [original-env @-env]
      (with-redefs [require identity
                    ns-resolve (fn [_ _] (fn []))]
        (it))
      (reset! -env original-env)))

  (it "logs when the task starts and completes"
    (let [info-log-invocations (atom [])]
      (with-redefs [info (mock-fn info-log-invocations)
                    task-namespaces (fn [] (list 'tenkai-suru.tasks.task-name))]
        (run-task "task-name" :environment)
        (should=
          [{:parameter "Starting tenkai-suru.tasks.task-name in :environment"}
           {:parameter "Finished tenkai-suru.tasks.task-name in :environment"}]
          @info-log-invocations))))

  (it "sets the specified environment"
    (with-redefs [task-namespaces (fn [] (list 'tenkai-suru.tasks.task-name))]
      (run-task "task-name" :some-arbitrary-env))
    (should= :some-arbitrary-env (environment)))

  (it "calls the execute function within the specified task's namespace"
    (with-redefs [task-namespaces (fn [] (list 'tenkai-suru.tasks.foo))]
      (should-invoke require {:with ['tenkai-suru.tasks.foo]}
        (should-invoke ns-resolve {:with ['tenkai-suru.tasks.foo 'execute] :return (fn [])}
          (run-task "foo" :test)))))

  (it "works when there are multiple tasks available"
    (with-redefs [task-namespaces (fn [] (list 'tenkai-suru.tasks.foo
                                               'tenkai-suru.tasks.bar
                                               'tenkai-suru.tasks.baz))]
      (should-invoke ns-resolve {:with ['tenkai-suru.tasks.bar 'execute] :return (fn [])}
        (run-task "bar" :test))))

  (it "logs an error when the task isn't found"
    (let [error-log-invocations (atom [])]
      (with-redefs [error (mock-fn error-log-invocations)
                    task-namespaces (fn [] (list))]
        (run-task "example" :environment)
        (should=
          [{:parameter "Could not find the 'tenkai-suru.tasks.example' task"}]
          @error-log-invocations))))

  (it "logs an error when the execute method isn't defined"
    (let [error-log-invocations (atom [])]
      (with-redefs [error (mock-fn error-log-invocations)
                    task-namespaces (fn [] (list 'tenkai-suru.tasks.foo))
                    ns-resolve (fn [_ _] nil)]
        (run-task "foo" :environment)
        (should=
          [{:parameter "You must define an 'execute' function in 'tenkai-suru.tasks.foo'"}]
          @error-log-invocations)))))
