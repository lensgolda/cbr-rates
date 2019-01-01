# cbr-rates

A Clojure library for retrieving exchange rates (for today or any date in past (history))
and currencies list through public API of (Russian Central Bank).

## Usage

```clojure
(:require [cbr-rates.core :as cbr])

;; today

(cbr/rates)
=>
({:name "Австралийский доллар",
  :nominal "1",
  :value "48,9351",
  :charcode "AUD",
  :numcode "036"}
  ...
 {:name "Японских иен",
  :nominal "100",
  :value "62,9976",
  :charcode "JPY",
  :numcode "392"})
(count *1)
=> 34

;; custom date in past

(cbr/rates "31/12/2018")
=>
({:name "Австралийский доллар",
  :nominal "1",
  :value "48,9351",
  :charcode "AUD",
  :numcode "036"}
  ...
 {:name "Японских иен",
  :nominal "100",
  :value "62,9976",
  :charcode "JPY",
  :numcode "392"})
```

Retrieve currencies list

```clojure
(cbr/currencies)
=>
({:name "Австралийский доллар", :nominal "1", :engname "Australian Dollar"}
 {:name "Австрийский шиллинг", :nominal "1000", :engname "Austrian Shilling"}
 {:name "Азербайджанский манат", :nominal "1", :engname "Azerbaijan Manat"}
 ...)
```

## License

Copyright © 2019 @lensgolda

Distributed under the MIT License.
