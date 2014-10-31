(ns tenkai-suru.core
  (:require
    [tenkai-suru.utility.render :refer [render-hiccup render-markdown]]
    [tenkai-suru.utility.middleware.log :refer [log-error log-request-and-response]]
    [compojure.core  :refer [defroutes GET]]
    [compojure.route :refer [not-found]]
    [ring.middleware.resource :refer [wrap-resource]]))

(defroutes application-routes
  (GET "/" request {:status 200
                    :headers {}
                    :body (render-hiccup "layout" {:page-content (render-markdown "overview")})})
  (not-found "Page Not Found"))

(def base-handler
  (-> application-routes
      (wrap-resource "public")
      log-error
      log-request-and-response))
