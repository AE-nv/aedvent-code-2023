(ns aoc-2023.day04-part1
  (:require [util :as util]
            [clojure.set :as set]))

(def example-input (list "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
                         "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
                         "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
                         "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83"
                         "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36"
                         "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"))
(comment
  (re-matches #"^Card (\d+): ([\s\d]+) \| ([\s\d]+)$" (first example-input))
  (re-matches #"^Card (\d+): (.+?) \| (.+?)$" (first example-input))
  ,,,)

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

(defn score-card [{winning :winning own :own}]
  (let [nb-of-wins (count (set/intersection winning own))]
    (if (> nb-of-wins 0)
      (->> (iterate (partial * 2) 1)
           (drop (dec nb-of-wins))
           (take 1)
           first)
      0)))

(defn score-deck-of-card [cards]
  (->> cards
       (map score-card)
       (reduce +)))

(comment
  (->> (util/file->seq "2023/day4.txt")
       parse-card-stack
       score-deck-of-card)

  (score-deck-of-card (parse-card-stack example-input))  
  (last (take 4 (iterate (partial * 2) 1)))
  
  (score-card (parse-card (first example-input))))


