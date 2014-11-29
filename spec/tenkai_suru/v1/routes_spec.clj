(ns tenkai-suru.v1.routes-spec
  (:require
    [ring.mock.request :refer [request]]
    [speclj.core :refer :all]
    [tenkai-suru.deploy.controller :as deploy-controller]
    [tenkai-suru.project.controller :as project-controller]
    [tenkai-suru.spec-helper :refer [test-handler test-response]]
    [tenkai-suru.v1.routes :refer :all]))


(describe "v1 routes"
  (describe "delegates to the deploy controller"
    (it "/project/:project-slug/deploy"
      (let [test-request (request :post "/project/some-project-slug/deploy")]
        (with-redefs [deploy-controller/create test-handler]
          (should=
            test-response
            (v1-handler test-request)))))

    (it "/project/:project-slug/deploy/:deploy-id"
      (let [test-request (request :get "/project/some-project-slug/deploy/:deploy-id")]
        (with-redefs [deploy-controller/info test-handler]
          (should=
            test-response
            (v1-handler test-request))))))

  (describe "delegates to the project controller"
    (it "/project/:project-slug/health-check"
      (let [test-request (request :get "/project/some-project-slug/health-check")]
        (with-redefs [project-controller/health-check test-handler]
          (should=
            test-response
            (v1-handler test-request)))))))
