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

(defn shuffled-board
  "Return shuffled cards."
  ([] (shuffled-board (all-cards)))
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
    {:player-id player-index :cards (map nice-card-print [card-1 card-2])}))

(defn deal-hands
  "Assigns cards to each player; returns a map with user hands and remaning cards in the deck."
  ([players-count] (deal-hands players-count (burn (shuffled-board))))
  ([players-count deck]
   (let [hands (map #(prepare-player-hand % deck players-count) (range 0 players-count))
        deck (burn deck (* players-count 2))]
     {:hands hands :deck deck})))

(defn deal-board
  "Return flop, turn and river."
  [deck]
  (let [deck (burn deck)]
    (let [flop (take 3 deck)
         turn (nth deck 4)
         river (nth deck 6)]
      {:flop flop :turn turn :river river})))

(defn -main []
  (println "Insert players count: ")
  (let [{hands :hands deck :deck} (deal-hands (Integer/parseInt (read-line)))]
    (println "\nPlayers hands: " hands)
    (let [{flop :flop turn :turn river :river} (deal-board deck)]
      (println "\nFlop: " (map nice-card-print flop))
      (println "Turn: " (nice-card-print turn))
      (println "River: " (nice-card-print river))
    )))
