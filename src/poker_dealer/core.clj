(ns poker-dealer.core
  (:gen-class))

(def card-colors {"spade" "♠", "heart" "♥",	"diamond" "♦", "club" "♣"})
(def card-denominations (concat (range 2 11) '("J" "Q" "K" "A")))

(defn cards-for-color
  "All cards / denominations for a color."
  ([color] (cards-for-color color card-denominations))
  ([color denominations]
   (map #(do {:denomination %, :color color}) denominations)))

(defn deck
  "All cards in the deck."
  ([] (deck (keys card-colors) card-denominations))
  ([colors denominations]
   (flatten (map cards-for-color colors))))

(defn print-card
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
  "Returns map with player ID and his cards."
  [player-index deck players-count]
  (let [card-1 (nth deck player-index)
        card-2 (nth deck (+ player-index players-count))]
    {:player-id player-index, :hand (map print-card [card-1 card-2])}))

(defn deal-hands
  "Assigns cards to each player; returns a map with user hands and remaning cards in the deck."
  ([players-count] (deal-hands players-count (-> (deck) shuffle burn)))
  ([players-count deck]
   (let [hands (map #(prepare-player-hand % deck players-count) (range 0 players-count))
         deck (burn deck (* players-count 2))]
     {:hands hands, :deck deck})))

(defn deal-board
  "Return flop, turn and river."
  [deck]
  (let [deck (burn deck)]
    (let [flop (take 3 deck)
          turn (nth deck 4)
          river (nth deck 6)]
      {:flop flop, :turn turn, :river river})))

(defn -main []
  "Shuffles cards, assigns hands to players and deals board cards."
  (println "Insert players count: ")
  (let [{hands :hands, deck :deck} (-> (read-line) Integer/parseInt deal-hands)]
    (println "\nPlayers hands: " hands)
    (let [{flop :flop, turn :turn, river :river} (deal-board deck)]
      (println "\nFlop: " (clojure.string/join " " (map print-card flop)))
      (println "Turn: " (print-card turn))
      (println "River: " (print-card river))
    )))
