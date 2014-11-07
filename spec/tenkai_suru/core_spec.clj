(ns tenkai-suru.core-spec
  (:require
    [ring.mock.request :refer [request]]
    [speclj.core :refer :all]
    [tenkai-suru.core :refer :all]
    [tenkai-suru.spec-helper :refer [silence-logger! test-handler test-response]]
    [tenkai-suru.v1.base-controller :refer [v1-handler]]))


(describe "core"
  (before
    (silence-logger!))

  (it "delegates all /v1 requests to the v1 handler"
    (let [test-request (request :get "/v1/something")]
      (with-redefs [v1-handler test-handler]
        (should=
          test-response
          (base-handler test-request))))))
