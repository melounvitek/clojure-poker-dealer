(ns poker-dealer.core-test
  (:require [clojure.test :refer :all]
            [poker-dealer.core :refer :all]))

(def deck-cards-count 52)
(def deck-colors-count 4)

(deftest deck-test
  (let [deck (deck) colors (keys card-colors)]
    (testing "Deck"
      (testing "has correct cards count"
        (is (= deck-cards-count (count deck))))
      (testing "contains all the colors"
        (is (= (count (set (map #(:color %) deck))) deck-colors-count))))))

(deftest burn-test
  (let [deck (deck)]
    (testing "Burn"
      (testing "works with one argument (burns one card)"
        (is (= (burn deck) (rest deck))))
      (testing "works with two arguments (burns multiple cards)"
        (is (= (burn deck 3) (drop 3 deck)))))))

(deftest deal-hands-test
  (testing "Deal hands"
    (testing "returns deck with correct cards count"
      (testing "for 2 players"
        (is (= (count (:deck (deal-hands 2))) (- deck-cards-count 5))))
      (testing "for 5 players"
        (is (= (count (:deck (deal-hands 5))) (- deck-cards-count 11)))))
    (testing "returns correct hands count"
      (testing "for random players count"
        (let [players-count (rand-nth (range 1 10))]
          (testing (str "(players count: " players-count ")")
            (is (= (count (:hands (deal-hands players-count)))
                   players-count))))))))

