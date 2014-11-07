(ns tenkai-suru.v1.base-controller-spec
  (:require
    [ring.mock.request :refer [request]]
    [speclj.core :refer :all]
    [tenkai-suru.spec-helper :refer [test-handler test-response]]
    [tenkai-suru.v1.base-controller :refer :all]
    [tenkai-suru.v1.project-controller :refer [project-handler]]))


(describe "v1 base controller"
  (it "delegates all /project requests to the project handler"
    (let [test-request (request :get "/project/some-project-slug")]
      (with-redefs [project-handler test-handler]
        (should=
          test-response
          (v1-handler test-request))))))
