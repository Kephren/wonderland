(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [rank ranks
        suit suits]
    [rank suit]))

(defn score [card] (.indexOf cards card))

(def shuffled (shuffle cards))
(def player1-hand (take-nth 2 shuffled))
(def player2-hand (take-nth 2 (drop 1 shuffled)))

(defn play-round [p1-card p2-card]
  (if (> (score p1-card) (score p2-card))
    :p1ayer1-won
    :player2-won))


(defn play-game [p1-cards p2-cards]
  (let [[p1-card & p1-deck] p1-cards
        [p2-card & p2-deck] p2-cards
        reward (shuffle [p1-card p2-card])]
    (cond
      (empty? p1-cards) "Player 2 Wins!"
      (empty? p2-cards) "Player 1 Wins!"
      ;reward player1
      (= (play-round p1-card p2-card)
         :player1-won)
      (recur (concat p1-deck reward) p2-deck)
      ;reward player2
      :else
      (recur p1-deck (concat p2-deck reward)))))

;; Not the same game.
(comment
  (defn play-wrong-game [player1-cards player2-cards]
    (let [score (->> (map play-round player1-cards player2-cards)
                     (frequencies))
          winner (->> score
                      (sort-by val >)
                      (ffirst))]
      (if (= (:p1 score)
             (:p2 score))
        :draw
        winner))))

