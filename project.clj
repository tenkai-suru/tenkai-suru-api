(defproject tenkai-suru-api "0.1.0"
  :description "API that enables deployments of any kind"
  :url "https://github.com/tenkai-suru/tenkai-suru-api"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [chee "2.0.0"]
                 [clj-stacktrace "0.2.8"]
                 [com.taoensso/timbre "3.2.1"]
                 [compojure "1.1.5"]
                 [markdown-clj "0.9.47"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring/ring-devel "1.2.0"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [stencil "0.3.4"]]
  :profiles {:test {:dependencies [[ring-mock "0.1.3"]
                                   [speclj "3.1.0"]]
                    :plugins [[speclj "3.0.2"]]
                    :resource-paths ["spec/resources" "resources"]}
             :dev {:main tenkai-suru.dev-main}
             :production {:main tenkai-suru.prod-main}
             :staging {:main tenkai-suru.staging-main}}
  :test-paths ["spec"]
  :aliases {"spec" ["with-profile" "test" "spec"]}
  :min-lein-version "2.0.0")
