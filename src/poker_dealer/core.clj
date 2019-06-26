(ns poker-dealer.core
  (:gen-class))

(def card-colors {"spade" "♠", "heart" "♥",	"diamond" "♦", "club" "♣"})
(def card-denominations (concat (range 2 11) '("J" "Q" "K" "A")))

(defn cards-for-color
  "All cards / denominations for a color."
  ([color] (cards-for-color color card-denominations))
  ([color denominations]
   (map #(do {:denomination % :color color}) denominations)))

(defn all-cards
  "All cards in the deck."
  ([] (all-cards (keys card-colors) card-denominations))
  ([colors denominations]
   (flatten (map cards-for-color colors))))

(defn shuffle-cards
  "Return shuffled cards."
  ([] (shuffle-cards (all-cards)))
  ([cards] (shuffle cards)))

(defn nice-card-print
  "Returns string with card denomination and color symbol."
  [card]
  (let [denomination (:denomination card)
        color (get card-colors (:color card))]
    (str denomination color)))

(defn burn
  "Burns the card(s) and returns deck."
  ([deck] (burn deck 1))
  ([deck count] (drop count deck)))

(defn prepare-player-hand
  [player-index cards players-count]
  (let [card-1 (nth cards player-index)
        card-2 (nth cards (+ player-index players-count))]
    {player-index (map nice-card-print [card-1 card-2])}))

(defn deal-hands
  "Assigns cards to each player; returns a map with user hands and remaning cards in the deck."
  [players-count deck]
  (let [players-hands (map #(prepare-player-hand % deck players-count) (range 0 players-count))
        deck (burn deck (* players-count 2))]
    {:players-hands players-hands :deck deck}
  ))

(defn deal
  ([] (deal (shuffle-cards)))
  ([deck]
   (println (map nice-card-print deck))
   (let [state-after-hands-dealt (deal-hands 3 (burn deck))]
     (println state-after-hands-dealt)
   )))

(defn -main []
  (deal))
