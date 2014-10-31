(ns tenkai-suru.utility.middleware.log-spec
  (:require
    [tenkai-suru.utility.log :as log]
    [tenkai-suru.utility.middleware.log :refer :all]
    [speclj.core :refer :all]))

(describe "logs any exceptions the app didn't catch"
  (it "returns a 500 on unhandled exceptions"
    (log/config-logger! {:filename "log/test-garbage.log" :to-standard-out? false})
    (let [handler (log-error (fn [request] (throw (Exception. "you broke stuff...good going"))))
          request {}
          response (handler request)]
      (should=
        500
        (:status response))))

  (it "pretty prints each incoming request"
    (let [handler (log-request-and-response (fn [request] {:status 200 :body "some arbitrary response"}))
          request {:uri "/some-uri"
                   :something-else {:sup "nmbro"}
                   :body "some arbitrary request body"}
          formatted-request-from-log (atom "")]
      (with-redefs [log/info (fn [thing-to-log] (reset! formatted-request-from-log thing-to-log))
                    request-count (atom 0)]
        (handler request)
        (should=
          (str "[RESPONSE] 1 ========================================================================================\n"
            "{\n"
            "    :body    23 chars of body\n"
            "    :status  200}\n")
          @formatted-request-from-log)))))
