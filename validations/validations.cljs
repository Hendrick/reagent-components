(ns hendrick.validations
    (:require [reagent.core :as reagent :refer [atom]]
              [cljsjs.react :as react]
              [bouncer.core :as b]))

(def required-message "Must be provided")
(def email-message    "Must be a valid email")
(def phone-regex      #"^\(\d{3}\) \d{3}-\d{4}$")
(def phone-message    "Must match format (999) 999-9999")
(def numeric-regex    #"^\d+$")
(def numeric-message  "Must be a number")
(def zip-regex        #"^\d{5}(-\d{4})?$")
(def zip-message      "Must be a 5-digit or 9-digit zip code")

(defn validate [e object-atom error-atom validator]
  (reset! error-atom
          (first (b/validate (deref object-atom) validator)))
  (if-not (b/valid? (deref object-atom) validator)
    (.preventDefault e)))

(defn validate-key [key object-atom error-atom validator]
  (let [key-map (select-keys (deref object-atom) [key])
        key-validator (select-keys validator [key])]
    (swap! error-atom assoc key
           (get (first (b/validate key-map key-validator)) key))))

(defn validated-field [key error-atom label body]
  (let [error (get (deref error-atom) key)]
    [:div.field {:class (if error "error" nil)}
     [:label label
             body
             [:small.error error]]]))
