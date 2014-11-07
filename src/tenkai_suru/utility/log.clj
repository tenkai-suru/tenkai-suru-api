(ns tenkai-suru.utility.log
  (:require
    [taoensso.timbre :as timbre]))


(defn info [message]
  (timbre/info message))

(defn error [message]
  (timbre/error message))

(defn fatal [message]
  (timbre/fatal message))

(defn config-logger! [{:keys [filename to-standard-out?]}]
  (timbre/set-config! [:appenders :spit :enabled?] true)
  (timbre/set-config! [:appenders :standard-out :enabled?] to-standard-out?)
  (timbre/set-config! [:shared-appender-config :spit-filename] filename))

