(ns tenkai-suru.utility.render
  (:require
    [markdown.core                :refer [md-to-html-string]]
    [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
    [ring.util.anti-forgery       :refer [anti-forgery-field]]
    [stencil.core                 :as stencil]
    [stencil.loader]))


(defn clear-template-cache []
  (stencil.loader/set-cache (clojure.core.cache/ttl-cache-factory {} :ttl 0)))

(defn render-hiccup
  ([page-name] (render-hiccup page-name {}))
  ([page-name map-to-render]
    (stencil/render-file
      (str "hiccup/" page-name ".hiccup")
      (assoc map-to-render
             :anti-forgery-token *anti-forgery-token*
             :anti-forgery-field (anti-forgery-field)))))

(defn render-markdown [file-name]
  (md-to-html-string (slurp (str "resources/markdown/" file-name ".md"))))
