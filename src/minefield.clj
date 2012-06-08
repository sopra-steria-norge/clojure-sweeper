(ns minefield
  (:use clojure.test))

(defn html-cell [cell] [:td {:class "mines-1"} cell])

(defn rows [minefield] (count minefield))
(defn cols [minefield] (count (first minefield)))

(defn minefield2 [] [[0 0] [0 0]])

