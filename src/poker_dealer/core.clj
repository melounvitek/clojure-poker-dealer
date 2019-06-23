(ns poker-dealer.core
  (:gen-class))

(def card-values (concat (range 2 11) ["J" "Q" "K" "A"]))
(def card-colors ["♠"	"♥"	"♦"	"♣"])

(defn color-values
  "Returns all cards for a given color."
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

(def memoized-cards (#(memoize shuffled-cards)))

(defn deal
  ([] (deal (memoized-cards)))
  ([cards]
   (println (first cards))
   (read-line)
   (deal (rest cards))))

(defn -main
  "Deals the cards."
  []
  (println (memoized-cards))
  (deal))
