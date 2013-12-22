(ns zakupki-gov-parser.test-parser
  (:use clojure.test
        zakupki-gov-parser.parser)
  (:import java.io.ByteArrayInputStream))

(def simple-entity {:table :simple-entity
                    :fields [[:c1 :c1]
                             [:c2 :c2]]})

(def no-such-entity {:table :no-such-entity
                     :fields [[:cc1 :cc1]
                              [:cc2 :cc2]]})

(def some-xml "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export>
  <simple>
    <c1>value-1</c1>
    <c2>value-2</c2>
    <c3>value-3</c3>
  </simple>
</export>")

(defn make-stream-from-string [s]
  (java.io.ByteArrayInputStream. (.getBytes s)))

#_(deftest test-entity-scheme-creation
  (is (= (parse-from-stream (make-stream-from-string some-xml)))))

(deftest test-simple-parse
  (is (= [] (parse-from-stream (make-stream-from-string some-xml)
                               [simple-entity]
                               print-entities))))
