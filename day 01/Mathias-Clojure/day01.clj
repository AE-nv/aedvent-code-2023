(ns aoc-2023.day01
  (:require [util :as util]
            [clojure.test :as test]))

(def example-input '("1abc2"
                    "pqr3stu8ewx"
                    "a1b2c3d4e5f"
                    "treb7uceet"))
(defn digit? [char]
  (java.lang.Character/isDigit char))

(defn grab-first-number-in-character-seq [chars]
  (->> chars
       (drop-while (fn [x] (not (digit? x))))
       (take 1)))

(defn parse-calibration [line]
  (let [first-digit (->> line
                         (grab-first-number-in-character-seq)
                         (apply str))
        last-digit (->> line
                        reverse
                        grab-first-number-in-character-seq
                        (apply str))]
    (util/str->long (str first-digit last-digit))))

(test/is
  (= (parse-calibration "1abc2") 12))
(test/is
  (= (parse-calibration "one287976") 26))

(defn read-calibrations [lines]
  (->> lines
       (map parse-calibration)
       (reduce +)))
(defn read-calibration-file [file-name]
  (->> file-name
       util/file->seq
       read-calibrations))

(comment
  (util/str->long "287976287976")
  ;; verify example input
  (+ 1 1)
  (digit? \4)
  (println example-input)
  (grab-first-number-in-character-seq "aaa234yy55")  
  (read-calibrations example-input)
  (read-calibration-file "2023/day1-part1.txt")
(->> "2023/day1-part1.txt"
       util/file->seq
       (drop 81)
       (take 1)
       ;;(map parse-calibration)
       ;;(reduce +)
       )
  ,,,)

(defn -main
  "Main function"
  []
  ())
