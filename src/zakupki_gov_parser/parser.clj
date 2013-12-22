(ns zakupki-gov-parser.parser
  (:require [clojure.xml :as xml]
            [clojure.inspector :as inspector]
            [zakupki-gov-parser.entityfuncs :as ef]
            [zakupki-gov-parser.db :as db]))


(defn node-is-list? [node]
  (and (> (count (:content node)) 1)
       (= 1 (count (set (map :tag (:content node)))))))

(defn extract-from-node [node tags]
  (let [tag (:tag node)
        content (:content node)
        extract #(extract-from-node % tags)]
    (if (tags tag)
      {tag (first content)}
      (if (nil? content)
        {}
        (if (node-is-list? node)
          (let [children-values (mapv #(extract-from-node % tags) content)
                non-empty-children (filterv #(> (count %) 0) children-values)]
            (if (empty? non-empty-children)
              {}
              {:__list__ non-empty-children}))
          (apply merge (mapv #(extract-from-node % tags) content)))))))

(defn construct-map-of-values [tree-desc]
  (if (contains? tree-desc :__list__)
    (map #(merge (dissoc tree-desc :__list__) %)
         (mapcat construct-map-of-values (:__list__ tree-desc)))
    (list tree-desc)))


(def path "/home/marat/Desktop/example-data")

(def entities (ef/list-entities))

(defn files-under-zip [zipfile]
  (enumeration-seq (.entries zipfile)))

(defn print-entities [entity-type entities-list]
  (println (:table entity-type) "*>" entities-list))

(defn parse-from-stream [input-stream entities process-entities]
  (let [parsed-xml (xml/parse input-stream)]
              (doseq [entity-type entities]
                (let [entities-list (-> parsed-xml
                                        (extract-from-node entity-type)
                                        construct-map-of-values)]
                  (println "123" parsed-xml #_(extract-from-node parsed-xml entity-type))
                  (process-entities entity-type entities-list)
                  #_(db/persist-entities! entity entities-list)))))

;TODO add directory recursive processing
(defn parse-all-from-directory [^String path entities process-entities]
  (doseq [file (file-seq (clojure.java.io/file path))]
    (when (and (.isFile file)
               (.endsWith (.getName file) ".zip"))
      (with-open [z (java.util.zip.ZipFile. file)]
        (doseq [f (files-under-zip z)]
          (with-open [fi (.getInputStream z f)]
            (parse-from-stream fi entities process-entities)))))))

#_(parse-all-from-directory path
                          entities
                          print-entities)


;;(def xml-file-name "/home/marat/Desktop/notification__Sankt-Peterburg_inc_20110101_000000_20110201_000000_1.xml")
;;
;;(def xml-file-name1 "/home/marat/Desktop/notification__Sankt-Peterburg_inc_20110101_000000_20110201_000000_1 (copy).xml")
;
;;(def lot-tags #{:oos:ordinalNumber :oos:notificationNumber :oos:createDate :oos:regNum})
;;
;;
;;
;; (def a1 (->
;;          xml-file-name
;;          xml/parse
;;          (extract-from-node lot-tags)
;;          construct-map-of-values
;;          inspector/inspect-table))
