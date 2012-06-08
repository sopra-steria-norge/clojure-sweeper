(ns clojure-sweeper.core
  (:use noir.core)
  (:use hiccup.core)
  (:use hiccup.page-helpers)
  (:use minefield)
  (:require [noir.server :as server]))

(defn html-minefield [minefield]
  (defn html-row  [row]  [:tr (map html-cell row)])
  [:div {:id "minefield"} [:table (map html-row minefield)]])

(defpage "/mines" []
  (html
    [:head (include-css "minefield.css")]
    [:body (html-minefield (minefield2))]
))

(defn -main []
  (server/start 1337))
