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

(def text-style {:color "white"
                 :fontWeight "bold"
                 :padding 10
                 :textAlign "center"
                 :width 50
                 :height 35})

(defn button [label color event code flag & style]
  [view  [touchable-highlight  {:style (merge style {:backgroundColor color
                                        :padding 10})
                                       
                                   :on-press #(dispatch [event code flag])}
          [text {:style {:color "white"
                         :text-align "center"
                         :font-weight "bold"}} label]]])



(defn color-board [red green blue]
  [view  {:style {:backgroundColor (str "rgb(" red "," green "," blue ")") 
                  :width 200 
                  :height 200 
                  :borderRadius 200 
                  }}])

(defn app-root []
  (let [red (subscribe [:red-code])
        green (subscribe [:green-code])
        blue (subscribe [:blue-code])
        current-color (subscribe [:current-color])]

    [view {:style {:backgroundColor "white" 
                   :flexDirection "column" 
                   :flex 1 :justifyContent "space-evenly" 
                   :margin 40 
                   :alignItems "center"}} 
                   
     [text {:style {:flexDirection "column" 
                    :marginTop 40
                    :fontSize 24 
                    :color @current-color
                    :alignitems "center"}} "Color mix "]
     (color-board @red @green @blue)
     [view {:style {:flexDirection "row" 
                    :flex 0.5 
                    :justifyContent "space-between" 
                    :align-items "center"}}
      [view 
       (button "+" "red" :change-red-code @red "inc" {:borderTopLeftRadius 10 :borderTopRightRadius 10 })
       [text{:style (merge text-style {:backgroundColor "red"})} "R"]
       (button "-" "red" :change-red-code @red "dec" {:borderBottomLeftRadius 10 :borderBottomRightRadius 10 })]
      [view {:style {:marginRight 10 :marginLeft 10}}
       (button "+" "green" :change-green-code @green "inc" {:borderTopLeftRadius 10 :borderTopRightRadius 10 })
       [text {:style (merge text-style {:backgroundColor "green"})} "G"]
       (button "-" "green" :change-green-code @green "dec"{:borderBottomLeftRadius 10 :borderBottomRightRadius 10 })]
      [view 
       (button "+" "blue" :change-blue-code @blue "inc" {:borderTopLeftRadius 10 :borderTopRightRadius 10 })
       [text {:style (merge text-style {:backgroundColor "blue" })} "B"]
       (button "-" "blue" :change-blue-code @blue "dec" {:borderBottomLeftRadius 10 :borderBottomRightRadius 10 })]]
      
      
     [view {:style {:borderRadius 80 :backgroundColor (str "rgb(" (+ 256 (* -1 @red)) ","(+ 256 (* -1 @green)) "," (+ 256 (* -1 @blue)) ")")  }}[image {:source logo-img
               :style  {:width 80 :height 80 }}]]
     [view {:style {:flex-direction "row"   
                    :align-items "center"
                    :marginTop 40
                    }}
      [text {:style (merge text-style {:backgroundColor @current-color})} @red]
      [text {:style (merge text-style {:backgroundColor  @current-color :marginLeft 10 :marginRight 10})} @green]
      [text {:style (merge text-style {:backgroundColor @current-color})} @blue]]
     (button "Reset" "black" :reset-code 0 "reset")]))



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
