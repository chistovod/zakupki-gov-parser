(ns zakupki-gov-parser.local-xml-parser
  (:use zakupki-gov-parser.parser))

(def path "/home/marat/Desktop/example-data")

(defn files-under-zip [zipfile]
  (enumeration-seq (.entries zipfile)))

(defn parse [path entities]
  (doseq [file (file-seq (clojure.java.io/file path))]
    (when (and (.isFile file)
               (.endsWith (.getName file) ".zip"))
      (with-open [z (java.util.zip.ZipFile. file)]
        (doseq [f (files-under-zip z)]
          (print (.getName f)))))))

(parse path [])
