(ns minefield
  (:use clojure.test))

(defn html-cell [minefield coord]
  [:td {:class "mines-1"} 
    ((minefield (first coord)) (second coord))])

(defn rows [minefield] (range 0 (count minefield)))
(defn cols [minefield] (range 0 (count (first minefield))))

(defn minefield [] [[0 0 \*] [0 0 0]])

