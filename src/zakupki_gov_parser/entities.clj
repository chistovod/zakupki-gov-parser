(ns zakupki-gov-parser.entities)

;; entities have
;; * fields with types
;; * xml-tag or filename mask


(def lot-entity #{:oos:ordinalNumber :oos:notificationNumber :oos:createDate :oos:regNum})

(def contact-entity #{:oos:firstName})

(defn list-entities []
  (let [internal-symbols (-> *ns*
                              ns-interns
                              keys)]
    (filter #(.endsWith (name %) "-entity") internal-symbols)))

(println (list-entities))
