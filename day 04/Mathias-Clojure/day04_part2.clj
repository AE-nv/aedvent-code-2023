(ns aoc-2023.day04-part2
  (:require [util :as util]
            [clojure.set :as set]))

(def debug (atom []))
(add-tap (partial swap! debug conj))


(def example-input (list "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
                         "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
                         "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
                         "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
                         "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
                         "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"))

(defn parse-numbers [nb-str]
  (->> (re-seq #"\d+" nb-str)
       (map util/to-int)
       (into #{})))

(defn parse-card [line]
  (let [[_ card-id winning own] (re-matches #"^Card\s+?(\d+): ([\s\d]+) \| ([\s\d]+)$" line)]
    {:id (util/to-int card-id)
     :winning (parse-numbers winning)
     :own (parse-numbers own)}))

(defn parse-card-stack [lines]
  (->> lines
       (map parse-card)))

(defn score-card [{winning :winning own :own :as card}]
  (let [nb-of-wins (count (set/intersection winning own))]
    (assoc card :score nb-of-wins)))

(defn add-new-cards [card-count-vec slot nb-cards-received]
  (let [nb-new-cards-for-subsequent-slots (nth card-count-vec slot)]
    (loop [new-card-count-vec card-count-vec
           update-slot (inc slot)
           remaining-slots nb-cards-received]
      (if (= remaining-slots 0)
        new-card-count-vec
        (recur
          (assoc new-card-count-vec update-slot (+ 
                                                  (nth new-card-count-vec update-slot)
                                                  nb-new-cards-for-subsequent-slots))
          (inc update-slot)
          (dec remaining-slots))))))
(comment
  (add-new-cards [4 1 1 1 1] 0 4)
  ,,,)

(defn play-card-game-better [lines]
  (let [initial-cards (parse-card-stack lines)
        nb-off-different-cards (count initial-cards)
        cards-received-by-slot (->> initial-cards
                                    (map score-card)
                                    (map (juxt :id :score))
                                    (map (fn [[id score]] [(dec id) score])) ;; use slot nb for ease of access
                                    (into {}))]
      (loop [card-count-vec (into [] (take nb-off-different-cards (repeat 1)))
             current-slot 0]
        (if (< current-slot nb-off-different-cards) 
          (recur (add-new-cards card-count-vec current-slot (cards-received-by-slot current-slot))
                 (inc current-slot))
          card-count-vec))
    ))

(comment
  (reduce + (play-card-game-better example-input))

  (->> (util/file->seq "2023/day4.txt")
       play-card-game-better
       (reduce +))
  ,,,)
