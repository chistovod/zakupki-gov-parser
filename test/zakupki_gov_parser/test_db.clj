(ns zakupki-gov-parser.test-db
  (:use clojure.test)
  (:use zakupki-gov-parser.db))

(def test-entity {:table :test-entity-name
                  :fields [[:someIdHere :oos:someIdHere :int "PRIMARY KEY AUTO_INCREMENT"]
                           [:notificationNumber :oos:notificationNumber]
                           [:createDate :oos:createDate]
                           [:regNum :oos:regNum]]})

(deftest test-entity-scheme-creation
  (is (= [:test-entity-name
          '(:someIdHere :int "PRIMARY KEY AUTO_INCREMENT")
          '(:notificationNumber "VARCHAR(32)")
          '(:createDate "VARCHAR(32)")
          '(:regNum "VARCHAR(32)")] (get-entity-scheme test-entity))))
