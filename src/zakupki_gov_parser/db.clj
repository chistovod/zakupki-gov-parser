(ns zakupki-gov-parser.db
  (:require [clojure.java.jdbc :as jdbc]
            [zakupki-gov-parser.entityfuncs :as ef]))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "zakupki.sqlite"})

(defn create-table-for-entity [entity]
  (jdbc/db-do-commands db-spec
                       (apply jdbc/create-table-ddl (ef/get-entity-scheme entity))))



(comment jdbc/insert! db-spec
              :lots
              {:id "my-id-is-2"})

(jdbc/query db-spec ["select * from lots"])

(comment println (jdbc/create-table-ddl
                  :lots
                  [:id "varchar"]))
