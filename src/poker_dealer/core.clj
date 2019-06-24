(ns poker-dealer.core
  (:gen-class))

(def card-colors {"spade" "♠", "heart" "♥",	"diamond" "♦",	"club" "♣"})

(defn colors-denominations-mapping
  [color denominations]
  (map (fn [denomination] {:denomination denomination :color color}) denominations))

(defn cards
  "All possible cards in deck."
  []
  (let [denominations (concat (range 2 11) ["J" "Q" "K" "A"])
        colors (keys card-colors)]

    (flatten (map #(colors-denominations-mapping % denominations) colors))))

(defn shuffle-cards
  "Return shuffled cards."
  ([] (shuffle-cards (cards)))
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
