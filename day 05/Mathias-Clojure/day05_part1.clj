(ns aoc-2023.day05-part1
  (:require [util :as util]))

;; identify maps, the maps are in order to output of one always leeds to the next
;; inside map, expand each line to full until a new map is encountered

;; reduce over set of maps or just comp them?

(def example-input (list
                     "seeds: 79 14 55 13"

                     "seed-to-soil map:"
                     "50 98 2"
                     "52 50 48"

                     "soil-to-fertilizer map:"
                     "0 15 37"
                     "37 52 2"
                     "39 0 15"

                     "fertilizer-to-water map:"
                     "49 53 8"
                     "0 11 42"
                     "42 0 7"
                     "57 7 4"

                     "water-to-light map:"
                     "88 18 7"
                     "18 25 70"

                     "light-to-temperature map:"
                     "45 77 23"
                     "81 45 19"
                     "68 64 13"

                     "temperature-to-humidity map:"
                     "0 69 1"
                     "1 0 69"

                     "humidity-to-location map:"
                     "60 56 37"
                     "56 93 4"))

(defn parse-seeds [line]
  (->> (re-seq #"(\d+)" line)
       (map (fn [[_ nb-str]] (util/str->long nb-str)))
       (into #{})))

(defn parse-resource-map-naive [lines]
  (reduce (fn [col x] 
            (let [[_ dest source l] (re-matches #"(\d+)\s+(\d+)\s+(\d+)\s*" x)
                  dest-start (util/str->long dest)
                  source-start (util/str->long source)
                  length (util/str->long l)]

              (into col 
                    (map (fn [src dest] [src dest]) 
                         (take length (iterate inc source-start)) 
                         (take length (iterate inc dest-start))))))
          {}
          lines))
(defn parse-resource-map
  "creates a function that acts like a map by validating each range"
  [lines]
  (let [range-mapping-fns (reduce (fn [col x] 
                                    (let [[_ dest source l] (re-matches #"(\d+)\s+(\d+)\s+(\d+)\s*" x)
                                          dest-start (util/str->long dest)
                                          source-start (util/str->long source)
                                          length (util/str->long l)]

                                      (conj col 
                                            (fn [i] (if (and (>= i source-start) (< i (+ source-start length)))
                                                      (+ (- i source-start) dest-start)
                                                      nil)))))
                                  [] 
                                  lines)]
    ;; function that checks all the individual mapping functions or defaults to the input if none match
    (fn [x] (or
              (some (fn [func] (func x)) range-mapping-fns)
              x))))

(comment
  ((parse-resource-map (list "50 98 2" "52 50 48")) 99)
  ,,,)

(defn parse-almanac [lines]
  (let [seeds (parse-seeds (first lines))
        resource-maps (->> (rest lines)
                             (filter (fn [line] (re-matches #"\S+.*" line)))
                             (util/split-using (fn [line] (re-matches #"\D+" line)))
                             (map parse-resource-map)
                             ;;(map (fn [m] (fn [x] (get m x x))))
                             )] ;;wrap with defaulting
    [seeds resource-maps]))

(defn chain-maps [maps]
  (apply comp (reverse maps)))

(defn find-nearest-location-to-start-farming-away [lines]
  (let [[seeds resource-maps] (parse-almanac lines)
        seed->location (chain-maps resource-maps)]
    (->> seeds
         (map seed->location)
         (apply min))))

(comment
  (find-nearest-location-to-start-farming-away example-input)

  (count (parse-almanac example-input))
  (let [[_ [f1 f2 f3 f4 f5 f6 f7 f8]] (parse-almanac (take 5 example-input))]
    (f1 98))

  (some (fn [func]) (list (fn [x] nil) (fn [x] 1)))

  (get (fn [x] nil) 3 2)

  (find-nearest-location-to-start-farming-away (util/file->seq "2023/day5.txt"))
  ,,,)
