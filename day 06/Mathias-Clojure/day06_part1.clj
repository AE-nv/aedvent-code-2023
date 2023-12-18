(ns aoc-2023.day06-part1
  (:require [util :as util]
            [clojure.math :as math]))

(def example-input (list 
                     "Time:      7  15   30"
                     "Distance:  9  40  200"))

(defn numbers-as-vector [string]
  (->> (util/extract-numbers string)
       (map util/str->long)
       (into [])))

(defn parse-race-document [[time-line distance-line]]
  (let [race-times-vec (numbers-as-vector time-line)
        race-distance-vec (numbers-as-vector distance-line)]

    (map (fn [time distance] {:time time :distance distance})
         race-times-vec 
         race-distance-vec)))

(def debug (atom []))
(defn add-to-debug [x]
  (swap! debug conj x))
(add-tap add-to-debug)

(defn analyse-race [{time :time distance :distance}]
  (let [min-dist-for-win (inc distance)
        [x0 x1] (sort (util/solve-kwadratic 1 (* -1 time) min-dist-for-win))
        we-win-starting (math/ceil x0)
        we-win-till (math/floor x1)
        nb-possible-wins (+ (- we-win-till we-win-starting) 1)]
    (do
      (add-to-debug [x0 x1 we-win-starting we-win-till])
      nb-possible-wins)))

(defn margin-of-error-across-races [lines]
  (->> lines
       parse-race-document
       (map analyse-race)
       (reduce *)))

(comment
  @debug
  
  
  (margin-of-error-across-races example-input)
  (margin-of-error-across-races (util/file->seq "2023/day6.txt"))
  
  
  (->> (parse-race-document example-input)
       ((fn [x] (nth x 2)))
       analyse-race)
  (->> (solve-kwadratic 1 -7 9)
       (sort))

  ;; result is always distance traveled + time powering up
  (parse-race-document example-input)
  ,,,)
