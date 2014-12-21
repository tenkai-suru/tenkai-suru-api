(defproject tenkai-suru-api "0.1.0"
  :description "API that enables deployments of any kind"
  :url "https://github.com/tenkai-suru/tenkai-suru-api"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [chee "2.0.0"]
                 [clj-stacktrace "0.2.8"]
                 [com.taoensso/timbre "3.2.1"]
                 [compojure "1.1.5"]
                 [environ "1.0.0"]
                 [markdown-clj "0.9.47"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [org.clojure/tools.namespace "0.2.7"]
                 [postgresql "9.1-901.jdbc4"]
                 [ragtime/ragtime.core "0.3.8"]
                 [ragtime/ragtime.sql.files "0.3.8"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [ring/ring-devel "1.2.0"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [stencil "0.3.4"]]
  :plugins [[lein-environ "1.0.0"]]

  :profiles {:test {:dependencies [[com.h2database/h2 "1.3.170"]
                                   [ring-mock "0.1.3"]
                                   [speclj "3.1.0"]]
                    :plugins [[speclj "3.1.0"]]
                    :resource-paths ["spec/resources" "resources"]
                    :test-paths ["spec"]
                    :env {:env :test}}

             :development {:aot [tenkai-suru.main.development]
                           :main tenkai-suru.main.development
                           :env {:env :development}}

             :staging {:aot [tenkai-suru.main.staging]
                       :main tenkai-suru.main.staging
                       :env {:env :staging}}

             :production {:aot [tenkai-suru.main.production]
                          :main tenkai-suru.main.production
                          :env {:env :production}}}

  :aliases {"spec"    ["with-profile" "test" "spec"]
            "task"    ["run" "-m" "tenkai-suru.main.task"]}

  :min-lein-version "2.0.0")
