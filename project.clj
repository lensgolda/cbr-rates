(defproject cbr-rates "0.1.0"
  :description "Library for receiving currency rates on a certain date, 
                from The Central Bank of the Russian Federation"
  :url "https://github.com/lensgolda/cb-currency-rates"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.xml "0.2.0-alpha5"]
                 [org.clojure/data.zip "0.1.2"]
                 [clj-http "3.9.0"]
                 [clj-time "0.15.1"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/clojure "1.10.0"]
                                  [reloaded.repl "0.2.4"]]}
             :1.8 {:dependencies [[org.clojure/clojure "1.8.0"]]}
             :1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]])
