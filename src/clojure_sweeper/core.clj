(ns clojure-sweeper.core
  (:use noir.core)
  (:require [noir.server :as server]))

(defpage "/" []
  "Hello Noir")

(defn -main []
  (server/start 1337))
