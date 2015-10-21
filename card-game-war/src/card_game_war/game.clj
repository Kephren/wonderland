(ns card-game-war.game)

;cards
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [rank ranks
        suit suits]
    [rank suit]))

(def shuffled (shuffle cards))
(def player1-deck (take-nth 2 shuffled))
(def player2-deck (take-nth 2 (drop 1 shuffled)))

(defn score [card] (.indexOf cards card))

(defn play-round [p1-card p2-card]
  (if (> (score p1-card) (score p2-card))
    :player1
    :player2))

(defn show-decks [p1-deck p2-deck]
  (println :p1-deck  (count p1-deck) p1-deck)
  (println :p2-deck (count p2-deck) p2-deck))

(defn play-game [p1-cards p2-cards]
  (let [[p1-card & p1-deck] p1-cards
        [p2-card & p2-deck] p2-cards
        reward (shuffle [p1-card p2-card])]
    (show-decks p1-cards p2-cards)
    (cond
      (empty? p1-cards) "Player 2 Wins Game!"
      (empty? p2-cards) "Player 1 Wins Game!"
      ;reward player1
      (= (play-round p1-card p2-card) :player1)
      (recur (concat p1-deck reward) p2-deck)
      ;reward player2
      :player2-won-round
      (recur p1-deck (concat p2-deck reward)))))

(println (play-game player1-deck player2-deck))
