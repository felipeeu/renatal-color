(ns renatal-color.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [renatal-color.events]
            [renatal-color.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defn button [label color event code]
  [view  [touchable-highlight  {:style {:backgroundColor color :padding 20 :marginLeft 10 :borderRadius 3} :on-press #(dispatch [event code])}  [text {:style {:color "white" :text-align "center" :font-weight "bold"}} label]]])

(defn color-board [red green blue]
 (println "Color-code: " red " " green " " blue)
 [view  {:style {:backgroundColor (str "rgb("red ","green","blue ")") :width 80 :height 50   :align-items "center"}}])

(defn app-root []
  (let [red (subscribe [:red-code])
        green (subscribe [:green-code])
        blue (subscribe [:blue-code])]
        
       (println "BLUE <<< " @blue)

    [view {:style {:backgroundColor "gray" :flex-direction "column" :flex 1 :justifyContent "space-evenly" :margin 40 :align-items "center" :borderWidth 2 }}
     [text {:style {:flex-direction "column" :marginTop 60 :align-items "center"}} "Color mix "]
    ;  [view  {:style {:backgroundColor (str "rgb("@red ","@green","@blue ")") :width 80 :height 50   :align-items "center"}}]
     (color-board @red @green @blue)
     [view {:style {:flex-direction "row" :flex 0.5 :justifyContent "space-between" :align-items "center"}}
      (button "R" "red" :change-red-code @red)
      (button "G" "green" :change-green-code @green)
      (button "B" "blue" :change-blue-code @blue)
      (button "reset" "pink" :reset-code 0)]
     [view {:style {:backgrondColor "blue" :flex-direction "row"   :margin 40 :align-items "center"}}
      [text {:style {:padding 10 :marginRight 20 :textAlign "center" :backgroundColor "white" :width "auto" :height "auto"}} @red]
      [text {:style {:padding 10 :marginRight 20 :textAlign "center" :backgroundColor "white" :width 50 :height 50}} @green]
      [text {:style {:padding 10 :textAlign "center" :backgroundColor "white" :width 50 :height 50}} @blue]]]))

; (defn app-root []
;   (let [greeting (subscribe [:get-greeting])]

;     (fn []
;       [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
;        [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}} @greeting]
;        [image {:source logo-img
;                :style  {:width 80 :height 80 :margin-bottom 30}}]
;        [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
;                              :on-press #(alert "HELLO!")}

;         [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])))


(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "renatalColor" #(r/reactify-component app-root)))
