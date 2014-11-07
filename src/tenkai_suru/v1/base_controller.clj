(ns tenkai-suru.v1.base-controller
  (:require
    [compojure.core  :refer [defroutes context]]
    [tenkai-suru.v1.project-controller :refer [project-handler]]))


(defroutes v1-handler
  (context "/project" [] project-handler))
