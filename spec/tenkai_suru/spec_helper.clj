(ns tenkai-suru.spec-helper
  (:require
    [tenkai-suru.utility.log :refer [config-logger!]]))


(defn silence-logger! []
  (config-logger! {:to-standard-out? false}))

(def test-response {:status 200 :headers {} :body "test-value"})
(defn test-handler [request] test-response)

(defn mock-fn [parameter-atom]
  (fn [parameter]
    (reset! parameter-atom (conj @parameter-atom {:parameter parameter}))))
