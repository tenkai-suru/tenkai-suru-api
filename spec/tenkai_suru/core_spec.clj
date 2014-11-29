(ns tenkai-suru.core-spec
  (:require
    [ring.mock.request :refer [request]]
    [speclj.core :refer :all]
    [tenkai-suru.core :refer :all]
    [tenkai-suru.spec-helper :refer [silence-logger! test-handler test-response]]
    [tenkai-suru.utility.render :refer [render-markdown]]
    [tenkai-suru.v1.routes :refer [v1-handler]]))


(describe "core"
  (before (silence-logger!))

  (it "delegates all /v1 requests to the v1 handler"
    (let [request (request :get "/v1/something")]
      (with-redefs [v1-handler test-handler]
        (should=
          test-response
          (base-handler request)))))

  (it "renders a welcome page for the '/' route"
    (let [request (request :get "/")
          response (base-handler request)]
      (should= 200 (:status response))
      (should-contain
        (render-markdown "overview")
        (:body response))))

  (it "returns a 404 if no handler is assigned to the request's route"
    (should= 404
      (:status (base-handler (request :get "/invalid-route"))))))
