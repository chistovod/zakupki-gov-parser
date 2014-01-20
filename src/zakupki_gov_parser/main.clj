(ns zakupki-gov-parser.main
  (:require [clojure.xml :as xml]
            [clojure.inspector :as inspector]
            [zakupki-gov-parser.entityfuncs :as ef]
            [zakupki-gov-parser.db :as db]
            [zakupki-gov-parser.parser :as p]))

(defn filter-interesting-nodes [node
                                ^clojure.lang.PersistentArrayMap interesting-tags])

(defn files-under-zip [zipfile]
  (enumeration-seq (.entries zipfile)))

(defn print-filename! [fn _]
  (println fn))

(defn on-each-file
  "Recursively walks all zip-files in a given directory and applies given func to each file"
  [^String path
   func]
  (doseq [file (file-seq (clojure.java.io/file path))]
    (when (and (.isFile file)
               (.endsWith (.getName file) ".zip"))
      (with-open [z (java.util.zip.ZipFile. file)]
        (doseq [f (files-under-zip z)]
          (with-open [fi (.getInputStream z f)]
            (func (.getName f) fi)))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;     Main
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def directory-with-example-files "/home/marat/Desktop/example-data")

(let [path directory-with-example-files
      entities (ef/list-entities)
      parse #(p/parse %1 %2 entities p/print-entities)]
  (on-each-file path parse))
