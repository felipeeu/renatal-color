(ns renatal-color.db
  (:require [clojure.spec.alpha :as s]))

;; spec of app-db
(s/def ::greeting string?)
(s/def ::app-db
  (s/keys :req-un [::greeting]))

;; initial state of app-db
(def app-db {:greeting "Hello Clojure in iOS and Android!" :red-code 0 :green-code 0 :blue-code 0 :current-color "black"})
