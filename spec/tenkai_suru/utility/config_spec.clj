(ns tenkai-suru.utility.config-spec
  (:require
    [tenkai-suru.utility.config :refer :all]
    [speclj.core :refer :all]))


(describe "config"
  (it "reads configuration values from the config directory"
    (should=
      {:some "test value"}
      (reader "test")))

  (it "reads configuration values from a directory specified by the 'CONFIG_HOME' env variable if set"
    (with-redefs [config-home (str (System/getProperty "user.dir") "/spec/resources/other-config")]
      (should=
        {:some "other test value"}
        (reader "test")))))
