(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is  (not (= 0 1))))
  (testing "queens are higher rank than jacks"
    (is (= (play-round [:jack :spade] [:queen :spade]) :player2)))
  (testing "kings are higher rank than queens"
    (is (= (play-round [:king :heart] [:queen :heart]) :player1)))
  (testing "aces are higher rank than kings"
    (is (= (play-round [:king :club] [:ace :club]) :player2)))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= (play-round [2 :club] [2 :spade]) :player1)))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= (play-round [2 :club] [2 :diamond]) :player2)))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= (play-round [2 :heart] [2 :diamond]) :player1))))

(deftest test-play-game
  (testing "the player loses when they run out of cards")
  (is (and (= (play-game [] player2-deck) "Player 2 Wins Game!")
           (= (play-game player1-deck ()) "Player 1 Wins Game!"))))

