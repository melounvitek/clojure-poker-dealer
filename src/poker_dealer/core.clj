(ns poker-dealer.core
  (:gen-class))

(def card-colors {"spade" "♠", "heart" "♥",	"diamond" "♦",	"club" "♣"})
(def card-denominations (concat (range 2 11) '("J" "Q" "K" "A")))

(defn cards-for-color
  "All cards / denominations for a color."
  ([color] (cards-for-color color card-denominations))
  ([color denominations]
   (map #(do {:denomination % :color color}) denominations)))

(defn all-cards
  "All cards in a deck."
  ([] (all-cards (keys card-colors) card-denominations))
  ([colors denominations]
   (flatten (map cards-for-color colors))))

(defn shuffle-cards
  "Return shuffled cards."
  ([] (shuffle-cards (all-cards)))
  ([cards] (shuffle cards)))

(def shuffled-cards (#(memoize shuffle-cards)))

(defn nice-card-print
  "Returns string with card denomination and color symbol."
  [card]
  (let [denomination (:denomination card)
        color (get card-colors (:color card))]
    (str denomination color)))

(defn deal-to-player
  [player-index cards players-count]
  (let [card-1 (nth cards player-index)
        card-2 (nth cards (+ player-index players-count))]
    {player-index (map nice-card-print [card-1 card-2])}))

(defn deal-to-players
  [players-count cards]
  (let [players-hands (map #(deal-to-player % cards players-count) (range 0 players-count))
        cards-after-deal (drop (* players-count 2) cards)]
    {:hands players-hands :cards (map nice-card-print cards-after-deal)}
  ))

(defn deal
  ([] (deal (shuffled-cards)))
  ([cards]
   (let [state-after-initial-deal (deal-to-players 3 (drop 1 cards))]
     (println state-after-initial-deal)
   )))

(defn -main []
  (println (map nice-card-print (shuffled-cards)))
  (deal))
