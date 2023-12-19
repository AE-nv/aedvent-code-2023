(ns aoc-2023.day07-part1
  (:require [util :as util]
            [clojure.test :as t]))

(def example-input (list "32T3K 765"
                         "T55J5 684"
                         "KK677 28"
                         "KTJJT 220"
                         "QQQJA 483"))

(def cards #{[\2 2] [\3 3] [\4 4] [\5 5] [\6 6] [\7 7] [\8 8] [\9 9] [\T 10] [\J 11] [\Q 12] [\K 13] [\A 14]})
(def cards-by-id (into {} (map (juxt first identity) cards)))

(def types [:high-card :pair :two-pair :three-o-kind :full-house :four-o-kind :five-o-kind])
(def priority-for-type (into {} (map-indexed (fn [idx type] [type idx]) types)))

(defn parse-hand [line]
  (let [[_ hand-str bid-str] (re-matches #"^(\S+) (\d+)$" line)
        bid (util/str->long bid-str)
        cards (reduce (fn [col c]
                        (conj col (cards-by-id c)))
                      []
                      hand-str)]
    {:cards cards :bid bid}))

(defn parse-hands [lines]
  (->> lines
       (map parse-hand)))

(comment
  (parse-hand (first example-input))
  (priority-for-type :full-house)
  (->> (parse-hands example-input)
       (map classify))

  ,,,)

(defn has-frequency? [f fs]
  (some (fn [freq] (= f freq)) (vals fs)))

(defn n-o-kind? [n card-freqs]
  (has-frequency? n card-freqs))

(defn full-house? [card-freqs]
  (and (has-frequency? 3 card-freqs)
       (has-frequency? 2 card-freqs)))

(defn count-pairs [card-freqs]
  (count (filter (fn [[_ f]] (= 2 f)) card-freqs)))

(defn two-pair? [card-freqs]
  (= 2 (count-pairs card-freqs)))

(defn pair? [card-freqs]
  (= 1 (count-pairs card-freqs)))

;; rank hand
(defn classify [{cards :cards :as hand}]
  (let [card-frequencies (frequencies cards)
        type  (cond
                (n-o-kind? 5 card-frequencies)
                :five-o-kind

                (n-o-kind? 4 card-frequencies)
                :four-o-kind

                (full-house? card-frequencies)
                :full-house

                (n-o-kind? 3 card-frequencies)
                :three-o-kind

                (two-pair? card-frequencies)
                :two-pair

                (pair? card-frequencies) 
                :pair

                :else
                :high-card
                )]
    (assoc hand :type type)))

(t/is
  (let [r (classify {:cards [[\1 1] [\1 1] [\1 1] [\1 1] [\1 1]]})]
    (= (r :type) :five-o-kind )))
(t/is
  (= :four-o-kind (classify {:cards [[\1 1] [\1 1] [\1 1] [\1 1] [\T 10]]})))
(t/is
  (= :full-house (classify {:cards [[\1 1] [\1 1] [\1 1] [\2 2] [\2 2]]})))
(t/is
  (= :two-pair (classify {:cards [[\1 1] [\2 2] [\2 2] [\4 4] [\4 4]]})))
(t/is
  (= :pair (classify {:cards [[\1 1] [\2 2] [\3 3] [\4 4] [\4 4]]})))
(t/is
  (= :high-card (classify {:cards [[\1 1] [\2 2] [\3 3] [\4 4] [\5 5]]})))

;; todo: create compare-in-order function?

(defn compare-in-order
  "Composes a comparator out of the provided comparators" 
  [& comparators]
  (fn [x y]
    (reduce (fn [col comp-fn]
              (let [comparison (comp-fn x y)]
                (if (not= comparison 0)
                  (reduced comparison)
                  comparison)))
            0
            comparators)))

(defn compare-by-type [{type-former :type} {type-latter :type}]
  (- (priority-for-type type-former) (priority-for-type type-latter)))

(defn compare-by-card [{cards-former :cards} {cards-latter :cards}]
  ;; this can be done using a reduce as well
  (if-let [label-difference (->> 
                              (map (fn [[_ label-former] [_ label-latter]]
                                     (- label-former label-latter))
                                   cards-former cards-latter)
                              (filter (fn [x] (not= x 0)))
                              first)]
    label-difference
    0))
(def compare-by-type-and-label (compare-in-order compare-by-type
                                                 compare-by-card))

(defn rank [hands]
  (->> hands
       (map classify)
       (sort compare-by-type-and-label)
       (map-indexed (fn [idx hand] (assoc hand :rank (inc idx))))))

(defn calculate-winnings [lines]
  (->> lines
       parse-hands
       rank
       (map (juxt :bid :rank))
       (reduce (fn [col [bid rank]] (+ col (* bid rank))) 0)))

(comment

  (calculate-winnings example-input)
  (calculate-winnings (util/file->seq "2023/day7.txt"))


  (compare-by-type-and-label {:cards [[\Q 12] [\Q 12] [\Q 12] [\J 11] [\A 14]],
                              :bid 483,
                              :type :three-o-kind}
                             {:cards [[\T 10] [\5 5] [\5 5] [\J 11] [\5 5]],
                              :bid 684,
                              :type :three-o-kind})

  (->> (parse-hands example-input)
       rank
       (map (juxt :bid :rank))
       (reduce (fn [col [bid rank]] (+ col (* bid rank))) 0))

  (classify {:cards [[\3 3] [\2 2] [\T 10] [\3 3] [\K 13]] :bid 20})
  ,,,)
