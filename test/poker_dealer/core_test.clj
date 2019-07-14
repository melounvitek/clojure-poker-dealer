(ns poker-dealer.core-test
  (:require [clojure.test :refer :all]
            [poker-dealer.core :refer :all]))

(def deck-cards-count 52)
(def deck-colors-count 4)
(def testing-deck [0 1 2 3 4 5 6 7 8 9 10])

(deftest deck-test
  (let [deck (deck) colors (keys card-colors)]
    (testing "Deck"
      (testing "has correct cards count"
        (is (= deck-cards-count (count deck))))
      (testing "contains all the colors"
        (is (= (count (set (map #(:color %) deck))) deck-colors-count))))))

(deftest card->string-test
  (are [card string] (= (card->string card) string)
    {:denomination "A", :color :spade} "A♠"
    {:denomination "K", :color :heart} "K♥"
    {:denomination "Q", :color :diamond} "Q♦"
    {:denomination "J", :color :club} "J♣"))

(deftest hand->string-test
  (let [hand
        {:player-id 1
        :hand [{:denomination "A", :color :spade} {:denomination "K", :color :heart}]}]
    (is (= (hand->string hand) "Player 2: A♠K♥"))))

(deftest burn-test
  (let [deck (deck)]
    (testing "Burn"
      (testing "works with one argument (burns one card)"
        (is (= (burn deck) (rest deck))))
      (testing "works with two arguments (burns multiple cards)"
        (is (= (burn deck 3) (drop 3 deck)))))))

(deftest prepare-player-hand-test
  (are
    [players-count player-index expected-cards]
    (= expected-cards
       (:hand (prepare-player-hand player-index testing-deck players-count)))
      2 0 [0 2]
      2 1 [1 3]
      4 1 [1 5]
      4 3 [3 7]))

(deftest deal-hands-test
  (testing "Deal hands"
    (testing "returns deck with correct cards count"
      (are
        [players-count]
        (= (count (:deck (deal-hands players-count)))
           (- deck-cards-count (+ (* players-count 2) 1)))
          2
          5))
    (testing "returns correct hands count"
      (testing "for random players count"
        (let [players-count (rand-nth (range 1 10))]
          (testing (str "(players count: " players-count ")")
            (is (= (count (:hands (deal-hands players-count)))
                   players-count))))))))

(deftest deal-board-test
  (is (= (deal-board testing-deck) [1 2 3 5 7])))

