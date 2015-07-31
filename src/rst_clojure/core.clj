(ns rst-clojure.core
 (:import
   [edu.arizona.sista.processors]
   [scala.io.Source]
   [edu.arizona.sista.struct DirectedGraphEdgeIterator]
   [edu.arizona.sista.processors.corenlp  CoreNLPProcessor]
   [edu.arizona.sista.processors.fastnlp FastNLPProcessor])
  (:require  
    [clojure.data.json :as json]
    [t6.from-scala.core :refer [$ $$] :as $]))
  

(defonce fast-proc (FastNLPProcessor. false false false true))

(defn rst-tree
  "Helper function for *get-rst-parse*.  Implements sista's RST parser. Returns an object
  of type scala.Some."
  [text]
  (let [doc (.mkDocument fast-proc text false)]
      (.tagPartsOfSpeech fast-proc doc )
      (.lemmatize fast-proc doc)
      (.parse fast-proc doc)
      (.discourse fast-proc doc)
   (. doc discourseTree)))
 

(defn get-rst-parse
  "Returns a json-formatted version of tree."
  [text]
  (let [tree (rst-tree text)
        discourse-tree ($/view tree);get class DiscourseTree from scala.Some object
        json-tree (.visualizerJSON discourse-tree)];json string version of tree to clojure hash-map
    (json/read-str json-tree :key-fn keyword)))

