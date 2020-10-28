(ns renatal-color.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :get-greeting
 (fn [db _]
   (:greeting db)))

(reg-sub
 :red-code
 (fn [db]
   (:red-code db)))

(reg-sub
 :green-code
 (fn [db]
   (:green-code db)))

(reg-sub
 :blue-code
 (fn [db]
   (:blue-code db)))

(reg-sub
 :current-color
 (fn [db]
   (:current-color db)))