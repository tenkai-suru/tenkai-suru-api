(ns tenkai-suru.main.task
  (:require
    [clojure.tools.namespace.find :as namespace]
    [environ.core :refer [env]]
    [tenkai-suru.utility.environment :refer [-env]]
    [tenkai-suru.utility.log :refer [config-logger! error info]]))


(defn task-namespaces []
  (namespace/find-ns-decls-in-dir
    (clojure.java.io/file "src/tenkai_suru/tasks")))

(defn find-in-tasks-dir [task-name]
  (first (filter
    #(re-find (re-pattern task-name) (str (last %)))
    (task-namespaces))))

(defn execute [task-namespace]
  ((or
    (ns-resolve task-namespace 'execute)
    (fn [] (error (str "You must define an 'execute' function in '" task-namespace "'"))))))

(defn run-task-in [task-namespace environment]
  (reset! -env environment)
  (info (str "Starting " task-namespace " in " environment))
  (require task-namespace)
  (execute task-namespace)
  (info (str "Finished " task-namespace " in " environment)))

(defn run-task [task-name environment]
  (let [[_ task-namespace] (find-in-tasks-dir task-name)]
    (if (nil? task-namespace)
      (error (str "Could not find the 'tenkai-suru.tasks." task-name "' task"))
      (run-task-in task-namespace environment))))

(defn -main [& args]
  (let [task-name (first args)]
    (config-logger! {:to-standard-out? true :filename "task"})
    (if-let [environment (env :env)]
      (run-task task-name environment)
      (error (str "You must set a valid environment you wish to run '" task-name "' in using profiles. Example: 'lein with-profile development task " task-name"'"))))
  (shutdown-agents))
