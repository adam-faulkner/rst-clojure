(defproject rst-clojure "0.1.0-SNAPSHOT"
  :description "A Clojure wrapper around sista's RST parser. "
  :dependencies [[org.clojure/clojure "1.5.1"]
               [org.clojure/data.json "0.2.6"]
               [org.scala-lang/scala-library "2.11.6"]
               [t6/from-scala "0.3.0-SNAPSHOT"]
               [edu.arizona.sista/processors_2.11 "5.3"]
               [edu.stanford.nlp/stanford-corenlp "3.5.1"]
               [edu.stanford.nlp/stanford-corenlp "3.5.1" :classifier "models"]
               [edu.stanford.nlp/srparser-models "3.5.1"]
               [nz.ac.waikato.cms.weka/weka-dev "3.7.10"]]
                 :plugins [[lein-localrepo "0.5.2"]])