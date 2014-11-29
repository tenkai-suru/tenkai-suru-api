(ns tenkai-suru.dev-main
  (:require
    [tenkai-suru.core :refer [base-handler]]
    [tenkai-suru.utility.environment :refer [-env]]
    [tenkai-suru.utility.log :refer [config-logger!]]
    [tenkai-suru.utility.render :as render]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.lint :refer [wrap-lint]]
    [ring.middleware.reload :refer [wrap-reload]]))


(def development-handler
  (->
    base-handler
    wrap-lint
    wrap-reload))

(defn -main [& args]
  (config-logger! {:to-standard-out? true})
  (render/clear-template-cache)
  (reset! -env :dev)

  (let [port (Integer/parseInt (or (first args) "4000"))]
    (run-jetty development-handler {:port port})))
