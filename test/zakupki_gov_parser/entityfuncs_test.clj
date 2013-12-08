(ns zakupki-gov-parser.test-entityfuncs
  (:use clojure.test)
  (:require [zakupki-gov-parser.entityfuncs :as e]))

(deftest create-fields-test
  (is (= [[:notificationNumber :oos:notificationNumber "VARCHAR(255)"]] (create-fields [:notificationNumber]))))
