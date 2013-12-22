(ns zakupki-gov-parser.db
  (:require [clojure.java.jdbc :as jdbc]
            [zakupki-gov-parser.entityfuncs :as ef]))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "zakupki.sqlite"})

(def DEFAULT_TYPE "VARCHAR(32)")

(defn get-entity-scheme [entity]
  (let [table (:table entity)
        fields (:fields entity)
        get-field-params (fn [field]
                           (let [column-name (first field)
                                 column-type (nth field 2 DEFAULT_TYPE)]
                             (concat [column-name column-type]
                                     (drop 3 field))))]
    (apply vector table (map get-field-params fields))))

(defn create-table-for-entity! [entity]
  (jdbc/db-do-commands db-spec
                       (apply jdbc/create-table-ddl
                              (get-entity-scheme entity))))


(defn persist-entities! [entity-type entities]
  (when (not-empty entities)
    (let [table-name (:table entity-type)
          fields (:fields entity-type)
          column-names (map first fields)
          transformers (map second fields)]
      (println "db-spec"
               table-name
               column-names
               entities
               (map (apply juxt transformers) entities)
               "====================="))))


(comment jdbc/insert! db-spec
              :lots
              {:id "my-id-is-2"})

(comment jdbc/query db-spec ["select * from lots"])

(comment println (jdbc/create-table-ddl
                  :lots
                  [:id "varchar"]))
