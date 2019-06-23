(ns poker-dealer.core
  (:gen-class))

;(def card-values ["2" "3" "4" "5" "6" "7" "8" "9" "10" "J" "Q" "K" "A"])
(def card-values (concat (range 2 11) ["J" "Q" "K" "A"]))
(def card-colors ["♠"	"♥"	"♦"	"♣"])

(defn color-values
  "Returns all cards for a color."
  [color]
  (map #(str % color) card-values))

(defn all-cards
  "Returns all possible cards."
  []
  (flatten (map color-values card-colors)))

(defn -main
  "Deals the cards."
  []
  (println (all-cards)))
