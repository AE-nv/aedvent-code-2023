(ns aoc-2023.day02-part2
  (:require [clojure.string :as s]
            [util :as util]))
 
(def example-input (list "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
                         "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
                         "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
                         "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
                         "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))

(defn parse-sample-set [set-str]
  (->> (re-seq #"(\d+?) ([a-z]+)" set-str)
       (map (fn [[_ nb color]] [color (util/to-int nb)]))
       (into {})))

(defn parse-game [line]
  (let [[_ id sets-section] (re-matches #"^Game (\d+)\: (.*)$" line)
        sample-sets (->> (s/split sets-section #";")
                         (map parse-sample-set))]
     {:game-id id
      :sample-sets sample-sets}))

(defn calculate-minimum-set [game]
  (->> (game :sample-sets)
       (apply merge-with max)))

(defn power-of [set]
  (reduce * (vals set)))

(defn total-powers-of-minimal-sets [file-name]
  (->> file-name
       util/file->seq
       (map (comp power-of calculate-minimum-set parse-game))
       (reduce +)))

(comment
  (total-powers-of-minimal-sets "2023/day2-part1.txt")
  ,,,)
