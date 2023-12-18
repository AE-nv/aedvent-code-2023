(ns aoc-2023.day03-part2
  (:require [util :as util]
            [clojure.set :as set]))

(def example-input (list 
                     "467..114.."
                     "...*......"
                     "..35..633."
                     "......#..."
                     "617*......"
                     ".....+.58."
                     "..592....."
                     "......755."
                     "...$.*...."
                     ".664.598.."))


;; parsing
(defn gear-cell? [ch] (= \* ch))

(defn parse-cell [cell-char-value]
  (cond 
        (and (char? cell-char-value) (java.lang.Character/isDigit cell-char-value))
        [:digit (util/char->int cell-char-value)]
        
        (gear-cell? cell-char-value)
        [:gear-symbol cell-char-value]
        
        :default
        [:void cell-char-value]))


(defn parse-grid [lines]
  (util/parse-grid-map parse-cell lines))

;; grid operations
(defn cell-type-is? [[_ [typ]] desired-type]
  (= typ desired-type))

(defn potential-gear-coordinates [grid]
  (->> grid
       (filter (fn [cell] (cell-type-is? cell :gear-symbol)))
       (map first)
       (into #{})))

(defn preceding [[x y]]
  [x (dec y)])


(defn aggregate-numbers-on-same-row [sorted-row]
  (reduce (fn [[[coord-set nb] & rest :as col] [coord [_ digit]]]
            (if (and (seq col) (coord-set (preceding coord)))
              (conj rest [(conj coord-set coord) (+ (* nb 10) digit)])
              (conj col [#{coord} digit])))
          []
          sorted-row))

(defn aggregate-numbers [grid]
  (let [sorted-rows (->> grid
                         (filter (fn [cell] (cell-type-is? cell :digit)))
                         (sort-by (fn [[coord _]] coord))
                         (group-by (fn [[[x _]]] x))
                         vals)]
    (->> sorted-rows
         (mapcat aggregate-numbers-on-same-row))))

(defn gear-ratio [gear-coord nb-by-coordinates]
  (let [touched-coord-set (->> (util/generate-surrounding-coordinates gear-coord)
                               (into #{}))
        nbs-linked-by-suspected-gear  (->> nb-by-coordinates
                                           (filter (fn [[coords _]] (seq (set/intersection coords touched-coord-set))))
                                           (map (fn [[_ number]] number)))]
    (if (= (count nbs-linked-by-suspected-gear) 2)
      (reduce * nbs-linked-by-suspected-gear)
      0)))

(defn sum-gear-ratios [grid-lines]
  (let [grid (parse-grid grid-lines)
        nb-by-coordinates (aggregate-numbers grid)
        potential-gear-coordinates (potential-gear-coordinates grid)]
    (->> potential-gear-coordinates
         (map (fn [coord] (gear-ratio coord nb-by-coordinates)))
         (reduce +))))

(comment
  (potential-gear-coordinates (parse-grid example-input))
  (sum-gear-ratios example-input)

  (->> (util/file->seq  "2023/day3.txt")
       sum-gear-ratios)

  ,,,)
