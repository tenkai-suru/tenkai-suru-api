(ns tenkai-suru.v1.routes
  (:require
    [compojure.core :refer [defroutes context POST GET]]
    [tenkai-suru.deploy.controller :as deploy-controller]
    [tenkai-suru.project.controller :as project-controller]))


(defroutes v1-handler
  (context "/project/:project-slug" []
    (POST "/deploy" request (deploy-controller/create request))
    (GET "/deploy/:deploy-id" request (deploy-controller/info request))
    (GET "/health-check" request (project-controller/health-check request))))
