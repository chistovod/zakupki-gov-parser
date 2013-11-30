(ns zakupki-gov-parser.db
  (:require [clojure.java.jdbc :as jdbc] ))

(def db-spec {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "zakupki.sqlite"})

(jdbc/db-do-commands db-spec
                     (jdbc/create-table-ddl
                      :lots
                      [:id "varchar"]))

(comment jdbc/insert! db-spec
              :lots
              {:id "my-id-is-1"})

(println (doall (jdbc/query db-spec
                            ["SELECT name FROM sqlite_master WHERE type = ?" "table"])))

;(jdbc/query db-spec ["select * from lots"])

(comment println (jdbc/create-table-ddl
                  :lots
                  [:id "varchar"]))
