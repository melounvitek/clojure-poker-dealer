(ns poker-dealer.core
  (:gen-class))


(defn cards
  "All possible cards in deck."
  []
  (let [denominations (concat (range 2 11) ["J" "Q" "K" "A"])
        colors ["♠"	"♥"	"♦"	"♣"]
        colors-denominations-mapping (fn [color] (map #(cons {:denomination % :color color} {}) denominations))]

    (flatten (map colors-denominations-mapping colors))))

(defn shuffle-cards
  "Return shuffled cards."
  ([] (shuffle-cards (cards)))
  ([cards] (shuffle cards)))

(def shuffled-cards (#(memoize shuffle-cards)))

(defn nice-print
  [card]
  (println (str (:denomination card) (:color card))))

(defn deal
  ([] (deal (shuffled-cards)))
  ([cards]
   (nice-print (first cards))
   (read-line)
   (deal (rest cards))))

(defn -main
  []
  (println (shuffled-cards))
  (deal))
