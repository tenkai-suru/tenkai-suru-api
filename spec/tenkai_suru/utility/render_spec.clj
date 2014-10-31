(ns tenkai-suru.utility.render-spec
  (:require
    [tenkai-suru.utility.render :refer :all]
    [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
    [speclj.core :refer :all]
    [stencil.core :refer [render-file]]))

(def render-file-args (atom nil))

(defn rendered-map []
  (second @render-file-args))

(describe "rendering hiccup"
  (describe "page content after rendering"
    (it "renders an email view with data in it (no layout)"
      (should=
        (str "<html><body>\nHey, Joe Schmo!\n\n"
             "Your url to sign up is <a href=\"http://www.example.com/123456\">here</a>.\n</body></html>\n")
        (render-hiccup "email"
          {:name "Joe Schmo" :url "http://www.example.com/123456"})))

    (it "renders a view with a partial and data in it"
      (should=
        "Hi\n<div>\nJoe\n</div>\n<div>\nSchmo\n</div>\n"
        (render-hiccup "passengers"
          {:passengers [{:name "Joe"} {:name "Schmo"}]}))))

  (describe "default parameters"
    (around [it]
      (with-redefs [render-file (fn [& args]
        (reset! render-file-args args))]))

    (it "includes the anti-forgery hidden field html (and the token for js consumers)"
      (binding [*anti-forgery-token* "9876-token-csrf-nope"]
        (render-hiccup "something" {})
        (should-contain "input" (:anti-forgery-field (rendered-map)))
        (should-contain "9876-token-csrf-nope" (:anti-forgery-field (rendered-map)))
        (should= "9876-token-csrf-nope" (:anti-forgery-token (rendered-map)))))))
