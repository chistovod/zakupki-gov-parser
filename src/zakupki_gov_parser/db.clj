(ns zakupki-gov-parser.db
  (:require [clojure.java.jdbc :as jdbc] ))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "zakupki.sqlite"})

(comment jdbc/db-do-commands db-spec
                     (jdbc/create-table-ddl
                      :lots
                      [:id "varchar"]))

(comment jdbc/insert! db-spec
              :lots
              {:id "my-id-is-2"})

(jdbc/query db-spec ["select * from lots"])

(comment println (jdbc/create-table-ddl
                  :lots
                  [:id "varchar"]))
