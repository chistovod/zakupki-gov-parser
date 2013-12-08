(ns zakupki-gov-parser.entityfuncs
  )


(defn get-xml-tags [entity]
  (mapv #(get % 1) (:fields entity)))

;; fix this
(defn list-entities []
  (let [internal-symbols (-> *ns*
                              ns-interns
                              keys)]
    (filter #(.endsWith (name %) "-entity") internal-symbols)))

(println (list-entities))
