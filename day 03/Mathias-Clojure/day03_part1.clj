(ns aoc-2023.day03-part1
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
(defn symbol-cell? [ch] (not= \. ch))

(defn parse-cell [cell-char-value]
  (cond 
        (and (char? cell-char-value) (java.lang.Character/isDigit cell-char-value))
        [:digit (util/char->int cell-char-value)]
        
        (symbol-cell? cell-char-value)
        [:symbol cell-char-value]
        
        :default
        [:void cell-char-value]))

(defn parse-grid [lines]
  (util/parse-grid-map parse-cell lines))

;; grid operations
(defn cell-type-is? [[_ [typ]] desired-type]
  (= typ desired-type))

(defn coord-touched-by-symbol-set [grid]
  (->> grid
       (filter (fn [cell] (cell-type-is? cell :symbol)))
       (mapcat (fn [[coord _]] (util/generate-surrounding-coordinates coord)))
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

(comment
  (aggregate-numbers-on-same-row '([[0 0] [:digit 4]]
  [[0 1] [:digit 6]]
  [[0 2] [:digit 7]]
  [[0 5] [:digit 1]]
  [[0 6] [:digit 1]]
  [[0 7] [:digit 4]]))

(defn aggregate-numbers [grid]
  (let [sorted-rows (->> grid
                         (filter (fn [cell] (cell-type-is? cell :digit)))
                         (sort-by (fn [[coord _]] coord))
                         (group-by (fn [[[x _]]] x))
                         vals)]
    (->> sorted-rows
         (mapcat aggregate-numbers-on-same-row))))
(comment
  (aggregate-numbers (parse-grid example-input))
  (aggregate-numbers [[[0 0] [:digit 4]]
  [[0 1] [:digit 6]]
  [[0 2] [:digit 7]]
  [[0 5] [:digit 1]]
  [[0 6] [:digit 1]]
  [[0 7] [:digit 4]]]
)
  (sort (list [1 1] [0 2] [0 1] [1 0])))

(defn sum-part-numbers [grid-lines]
  (let [grid (parse-grid grid-lines)
        touched-coord-set (coord-touched-by-symbol-set grid)
        nb-by-coordinates (aggregate-numbers grid)
        touched-numbers (->> nb-by-coordinates
                             (filter (fn [[coords _]] (seq (set/intersection coords touched-coord-set))))
                             (map (fn [[_ number]] number)))]
    (println touched-coord-set)
    (reduce + touched-numbers)))

(comment
  (sum-part-numbers example-input)
  (->> (util/file->seq  "2023/day3.txt")
       sum-part-numbers)

  ,,,)
