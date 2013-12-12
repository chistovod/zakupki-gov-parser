(ns zakupki-gov-parser.entityfuncs)


(def DEFAULT_TYPE "VARCHAR(32)")

(defn get-xml-tags [entity]
  (set (mapv #(get % 1) (:fields entity))))

(defn get-entity-scheme [entity]
  (let [table (:table entity)
        fields (:fields entity)
        get-field-params (fn [field]
                           (let [column-name (first field)
                                 column-type (nth field DEFAULT_TYPE)])
                           (concat [column-name column-type] (drop 3 field)))])
  (apply vector table (map get-field-params fields)))

;; fix this
(defn list-entities []
  (let [internal-symbols (-> *ns*
                              ns-interns
                              keys)]
    (filter #(.endsWith (name %) "-entity") internal-symbols)))

(println (list-entities))
