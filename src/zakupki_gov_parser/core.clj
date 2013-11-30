(ns zakupki-gov-parser
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]
            [clojure.inspector :as inspector]))

(def xml-file-name "/home/marat/Desktop/notification__Sankt-Peterburg_inc_20110101_000000_20110201_000000_1.xml")

(def xml-file-name1 "/home/marat/Desktop/notification__Sankt-Peterburg_inc_20110101_000000_20110201_000000_1 (copy).xml")

(def lot-tags #{:oos:ordinalNumber :oos:notificationNumber :oos:createDate :oos:regNum})

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

(defn construct-objects [desc]
  (if (contains? desc :__list__)
    (map #(merge (dissoc desc :__list__) %)
         (mapcat construct-objects (:__list__ desc)))
    (list desc)))

(def a1 (->
         xml-file-name1
         xml/parse
         (extract-from-node lot-tags)
         construct-objects
         inspector/inspect-table))
