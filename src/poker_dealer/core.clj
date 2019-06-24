(ns poker-dealer.core
  (:gen-class))

(def card-colors {"spade" "♠", "heart" "♥",	"diamond" "♦",	"club" "♣"})
(def card-denominations (concat (range 2 11) ["J" "Q" "K" "A"]))

(defn cards-for-color
  "All cards / denominations for a color."
  ([color] (cards-for-color color card-denominations))
  ([color denominations]
   (map (fn [denomination] {:denomination denomination :color color}) denominations)))

(defn all-cards
  "All possible cards in deck."
  ([] (all-cards (keys card-colors) card-denominations))
  ([colors denominations]
   (flatten (map cards-for-color colors))))

(defn shuffle-cards
  "Return shuffled cards."
  ([] (shuffle-cards (all-cards)))
  ([cards] (shuffle cards)))

(def shuffled-cards (#(memoize shuffle-cards)))

(defn nice-card-print
  [card]
  (println (str (:denomination card) (get card-colors (:color card)))))

(defn deal
  ([] (deal (shuffled-cards)))
  ([cards]
   (nice-card-print (first cards))
   (read-line)
   (deal (rest cards))))

(defn -main
  []
  (println (shuffled-cards))
  (deal))
