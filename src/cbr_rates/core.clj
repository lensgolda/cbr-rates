(ns cbr-rates.core
  (:require
    [clj-http.client :as client]
    [clojure.data.xml :as data-xml]
    [clojure.data.zip.xml :as data-zip-xml]
    [clj-time.core :as time]
    [clj-time.format :as time-format]))

;; CBR - The Central Bank of the Russian Federation

;; Sample XML file of daily currency rates
(def dev-xml
  (slurp "resources/XML_daily.xml" :encoding "windows-1251"))

;; The CBR API URL for receiving daily currency rates
(def ^:private xml-daily-base-url
  "http://www.cbr.ru/scripts/XML_daily.asp")

;; The CBR URL for receiving list of currencies
(def ^:private xml-currencies-url
  "http://www.cbr.ru/scripts/XML_val.asp?d=0")

;; DateTime formatter for currency rates CBR request
(def ^:private cb-formatter (time-format/formatter "dd/MM/yyyy"))

;; Receives currencies xml response
(def ^:private currencies-xml
  (future (client/get xml-currencies-url {:as "windows-1251"})))

;; Receives currency rates xml reponse
(defn- rates-xml
  [date]
  (future (client/get (str xml-daily-base-url "?date_req=" date) {:as "windows-1251"})))

;; Converts XMLElement to clojure map structure
(defn- elems->map
  [elem item-name]
  (let [zip  (clojure.zip/xml-zip elem)
        item-name (keyword item-name)
        item (partial data-zip-xml/xml1-> zip
                                          item-name)]
    (cond-> {:name     (item :Name data-zip-xml/text)
             :nominal  (item :Nominal data-zip-xml/text)}
      (= item-name :Valute) (assoc :value    (item :Value data-zip-xml/text)
                                   :charcode (item :CharCode data-zip-xml/text)
                                   :numcode  (item :NumCode data-zip-xml/text))
      (= item-name :Item)   (assoc :engname  (item :EngName data-zip-xml/text)))))

;; Parses XML to Clojure map
(defn- xml->map
  [xml data-type]
  (let [data (data-xml/parse-str (:body @xml))]
    (->> (:content data)
         (remove string?)
         (map #(elems->map % data-type)))))

;; Get currency rates list
(defn rates
  "Get currency rates list on a certain date or for current date, if date param ommited."
  ([]
   (rates (time-format/unparse cb-formatter (time/now))))
  ([date]
   (-> (rates-xml date)
       (xml->map :Valute))))

;; Get currencies
(defn currencies
  "Get currencies list, where each currency contains nominal, currency code and currency name values"
  []
  (-> currencies-xml
      (xml->map :Item)))
