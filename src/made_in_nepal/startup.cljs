(ns ^:figwheel-always made-in-nepal.startup
  (:require [om.core :as om :include-macros true]
            [sablono.core :refer-macros [html]]
            [om-tools.core :refer-macros [defcomponent]]))


(defcomponent startup
  [startup owner opts]
  (render [_]
     (html [:li {:class "flex-item"}
           (str (:name startup) " " (:employees startup))
            ])))
