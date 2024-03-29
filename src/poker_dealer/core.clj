(ns poker-dealer.core
  (:gen-class))

(def card-colors {:spade "♠", :heart "♥",	:diamond "♦", :club "♣"})
(def card-denominations (concat (range 2 11) '("J" "Q" "K" "A")))
(def hand->string-text "Player %d: %s")

(defn cards-for-color
  "All cards / denominations for a color."
  ([color] (cards-for-color color card-denominations))
  ([color denominations]
   (map #(hash-map :denomination %, :color color) denominations)))

(defn deck
  "All cards in the deck."
  ([] (deck (keys card-colors) card-denominations))
  ([colors denominations]
   (mapcat cards-for-color colors)))

(defn card->string
  "Returns string with card denomination and color symbol."
  [{:keys [color denomination]}]
  (let [color (get card-colors color)]
    (str denomination color)))

(defn hand->string
  "Returns player and his hand."
  [{:keys [player-id hand]}]
  (format hand->string-text
          (inc player-id)
          (clojure.string/join (map card->string hand))))

(defn burn
  "Burns the card(s) and returns deck."
  ([deck] (burn deck 1))
  ([deck count] (drop count deck)))

(defn prepare-player-hand
  "Returns map with player ID and his cards."
  [player-index deck players-count]
  (let [card-1 (nth deck player-index)
        card-2 (nth deck (+ player-index players-count))]
    {:player-id player-index, :hand [card-1 card-2]}))

(defn deal-hands
  "Assigns cards to each player; returns a map with user hands and remaning
   cards in the deck."
  ([players-count] (deal-hands players-count (-> (deck) shuffle burn)))
  ([players-count deck]
   (let [hands (map #(prepare-player-hand % deck players-count)
                    (range 0 players-count))
         deck (burn deck (* players-count 2))]
     {:hands hands, :deck deck})))

(defn deal-board
  "Returns flop, turn and river."
  [deck]
  (let [deck (burn deck)
        flop (take 3 deck)
        turn (nth deck 4)
        river (nth deck 6)]
    (flatten [flop turn river])))

(defn -main
  "Shuffles cards, assigns hands to players and deals board cards."
  ([]
   (println "Insert players count: ")
   (-main (read-line)))
  ([count]
   (let [{hands :hands, deck :deck} (-> count Integer/parseInt deal-hands)]
     (println (map hand->string hands))
     (println (map card->string (deal-board deck))))))
