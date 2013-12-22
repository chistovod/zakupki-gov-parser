(ns zakupki-gov-parser.entityfuncs
  (:use zakupki-gov-parser.entities))

(defn get-xml-tags [entity]
  (set (mapv #(get % 1) (:fields entity))))

#_(defn list-entities []
  (let [entities-ns (find-ns 'zakupki-gov-parser.entities)
        internal-symbols (-> entities-ns
                             ns-interns
                             keys)
        entity-symbols (filter #(.endsWith (name %) "-entity")
                               internal-symbols)]
    (println entity-symbols)
    (println (type  entity-symbols))
    (doall (map #(% (.getMappings entities-ns)) entity-symbols))
    (loop [e entity-symbols]
      (e (.getMappings entities-ns)))))

(defn list-entities
  []
  [lot-entity])

(println  (list-entities))
