(ns ^:figwheel-always made-in-nepal.core
  (:require[om.core :as om :include-macros true]
           [sablono.core :refer-macros [html]]
           [alandipert.storage-atom :refer [local-storage]]
           [made-in-nepal.startup :refer [startup]]
           ))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(def keyword-map {
                  :all "All"
                  :less-than-10-employees "Less than 10 employees"
                  :less-than-20-employees "Less than 20 employees"})

(def filters {
              :all {:text "All" :filter (fn [startup] true)}
              :less-than-10-employees {:text "Less than 10" :filter (fn [startup] (< (:employees startup) 10))}
              :less-than-20-employees {:text "Less than 20" :filter (fn [startup] (< (:employees startup) 20))}
              })



(defonce app-state
 ; (local-storage
   (atom {:startups
          [{:name "Parewa Labs" :employees 5}
           {:name "Kathmandu Living Labs" :employees 20}
           {:name "Janaki Tech" :employees 25}]
          :current-filter (:filter (:all filters))}

  ;  )
 ;  :todo-app-state
   )
  )



(defn display-filters
  [data owner]
  (om/component
   (html
    [:div
     (for [todo-filter filters]
       [:a
        {:class (if (= (:current-filter data) (:filter (nth todo-filter 1))) "todo-filter active" "todo-filter")
         :on-click #(om/transact! data :current-filter (fn [current-filter] (:filter (nth todo-filter 1))))}
        (:text (nth todo-filter 1))])])))


(defn startups-component
  [data owner]
  (om/component
   (html
    [:div
     [:h1 "Startups In Nepal"]
     (om/build display-filters data)
     [:ul
      (om/build-all startup (filterv (:current-filter data) (:startups data) ))]
     ])))

(om/root startups-component
         app-state
         {:target (. js/document (getElementById "startups"))})
