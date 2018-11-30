(ns cb-currency-rates.core-test
  (:require [clojure.test :refer :all]
            [cbr-rates.core :as cbr]
            [clj-http.client :as client]))

(def ^:private rates-xml-data "<?xml version=\"1.0\" encoding=\"windows-1251\" ?><ValCurs Date=\"05.12.2017\" name=\"Foreign Currency Market\"><Valute ID=\"R01010\"><NumCode>036</NumCode><CharCode>AUD</CharCode><Nominal>1</Nominal><Name>Австралийский доллар</Name><Value>44,8568</Value></Valute></ValCurs>")

(deftest rates-test
  (testing "Returns currency rates list"
    (with-redefs [client/get (fn [_ _] {:body rates-xml-data})]
      (is (= (cbr/rates)
             [{:name "Австралийский доллар"
               :nominal "1"
               :value "44,8568"
               :charcode "AUD"
               :numcode "036"}])))))
