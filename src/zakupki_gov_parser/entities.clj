(ns zakupki-gov-parser.entities)

;; entities have
;; * fields with types
;; * xml-tag mask


;;; fields are tuples (field-name-in-db field-name-in-xml type additional-parameter-as-string)
;;; i.e. [notificationNumber :oos:notificationNumber :int "PRIMARY KEY"]
;;; or [notificationNumber :oos:notificationNumber "VARCHAR(255)"]


(def lot-entity {:table :lots
                 :fields [[:ordinalNumber :oos:ordinalNumber :int]
                          [:notificationNumber :oos:notificationNumber]
                          [:createDate :oos:createDate]
                          [:regNum :oos:regNum]]} )
