(ns zakupki-gov-parser.test-parser
  (:require [clojure.xml :as xml])
  (:use clojure.test
        zakupki-gov-parser.parser)
  (:import java.io.ByteArrayInputStream))

(def simple-entity {:table :simple-entity
                    :fields [[:c1 :c1]
                             [:c2 :c2]]})

(def no-such-entity {:table :no-such-entity
                     :fields [[:cc1 :cc1]
                              [:cc2 :cc2]]})

(defn to-stream [s]
  (java.io.ByteArrayInputStream. (.getBytes s)))

(def single-xml (xml/parse (to-stream
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<k>v</k>")))

(def sample-xml (xml/parse (to-stream
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<export>
  <data>
    <c1>value-1</c1>
    <c2>value-2</c2>
    <c3>value-3</c3>
    <xs>
      <x>
        <c4>value-4.1</c4>
      </x>
      <x>
        <c4>value-4.2</c4>
      </x>
    </xs>
  </data>
</export>")))


(deftest test-filter-tags-single-valie
  (is (= {:k "v"} (filter-tags single-xml #{:k}))))


(deftest test-filter-tags-flat-xml
  (is (= {:c1 "value-1",
          :c2 "value-2"} (filter-tags sample-xml #{:c1 :c2}))))

(deftest test-filter-tags
  (is (= {:c1 "value-1",
          :c2 "value-2",
          :__list__ [{:c4 "value-4.1"}
                     {:c4 "value-4.2"}]}
         (filter-tags sample-xml #{:c1 :c2 :c4}))))
