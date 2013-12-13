(ns zakupki-gov-parser.test-entityfuncs
  (:use clojure.test)
  (:use zakupki-gov-parser.entityfuncs))

(def test-entity {:table :test-entity-name
                  :fields [[:someIdHere :oos:someIdHere :int "PRIMARY KEY AUTO_INCREMENT"]
                           [:notificationNumber :oos:notificationNumber]
                           [:createDate :oos:createDate]
                           [:regNum :oos:regNum]]})

(deftest create-table-for-entity
  (is (= [:test-entity-name
          '(:someIdHere :int "PRIMARY KEY AUTO_INCREMENT")
          '(:notificationNumber "VARCHAR(32)")
          '(:createDate "VARCHAR(32)")
          '(:regNum "VARCHAR(32)")] (get-entity-scheme test-entity))))
