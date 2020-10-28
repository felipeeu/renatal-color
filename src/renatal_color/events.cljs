(ns renatal-color.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
   [clojure.spec.alpha :as s]
   [renatal-color.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

(def validate-spec
  (if goog.DEBUG
    (after (partial check-and-throw ::db/app-db))
    []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
 validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :set-greeting
 validate-spec
 (fn [db [_ value]]
   (assoc db :greeting value)))

(reg-event-db
 :change-red-code
 (fn [db [_ code flag]]

   (assoc db  :red-code (if (= "inc" flag) (+ 8 code) (+ -1 code) ) :current-color "red")))

(reg-event-db
 :change-green-code
 (fn [db [_ code flag]]
   (assoc db  :green-code (if (= "inc" flag) (+ 8 code) (+ -1 code) ) :current-color "green")))

(reg-event-db
 :change-blue-code
 (fn [db [_ code flag]]
   (assoc db  :blue-code (if (= "inc" flag) (+ 8 code) (+ -1 code) ) :current-color "blue")))

(reg-event-db
 :reset-code
 (fn [db [_ code _]]
   (assoc db :red-code code :green-code code :blue-code code :current-color "black")))

