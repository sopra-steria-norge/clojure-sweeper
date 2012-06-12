(ns clojure-sweeper.core
  (:use noir.core)
  (:use hiccup.core)
  (:use hiccup.page-helpers)
  (:use minefield)
  (:require [noir.server :as server]))

(defn html-minefield [minefield]
  (defn html-row  [row-index] 
    [:tr (map #(html-cell minefield [row-index %]) (cols minefield))])
  [:div {:class "minefield"}
    [:table (map html-row (rows minefield))]])

(defpage "/" []
  (html
    [:head (include-css "minefield.css")]
    [:body (html-minefield (minefield))]
))

(defn -main []
  (server/start 1337))
