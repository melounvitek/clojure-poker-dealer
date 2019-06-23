(ns poker-dealer.core
  (:gen-class))

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

(defn shuffled-cards
  "Shuffle cards."
  ([] (shuffled-cards (all-cards)))
  ([cards] (shuffle cards)))

(defn -main
  "Deals the cards."
  []
  (println (shuffled-cards)))
