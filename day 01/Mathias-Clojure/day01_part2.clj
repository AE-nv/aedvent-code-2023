(ns aoc-2023.day01-part2
  (:require [util :as util]
            [clojure.string :as s]))

(defn digit? [char]
  (java.lang.Character/isDigit char))

(defn look-for-spelled-out-digit [window-str]
  (condp (fn [x window] (s/ends-with? window x)) window-str
    "one" 1
    "two" 2
    "three" 3
    "four" 4
    "five" 5
    "six" 6
    "seven" 7
    "eight" 8
    "nine" 9
    "zero" 0
     nil))

(comment (look-for-spelled-out-digit "three")
         (s/ends-with? "" "two"))

(defn look-for-digit [ch window]
  (if (digit? ch)
    (util/str->long (str ch))
    (look-for-spelled-out-digit window)))

(comment (look-for-digit \o "ghtwo")
         (slide "abcef" \d))

(defn slide [window-str ch]
  (str
    (if (= (count window-str) 5) (subs window-str 1) window-str)
    ch))

(defn scan-calibration-line
  "Scanning window solution, checks for a digit on the current character, if that is not a digit,
  check the window for the spelled out version"
  [line]
  (let  [[_ first last] (reduce (fn [[window-str first-digit last-digit] char]
                                  (let [new-window-str (slide window-str char)
                                        digit (look-for-digit char new-window-str)]
                                    (if digit
                                      (if first-digit
                                        [new-window-str first-digit digit]
                                        [new-window-str digit digit])
                                      [new-window-str first-digit last-digit])))
                                [""]
                                line)]
    (+ (* 10 first) last)))

(defn read-calibrations [lines]
  (->> lines
       (map scan-calibration-line)
       (reduce +)))

(defn read-calibration-file [file-name]
  (->> file-name
       util/file->seq
       read-calibrations))

(comment
  (read-calibration-file "2023/day1-part1.txt")
  (scan-calibration-line "eightwothree")
  ,,,)

(defn -main
  "Main function"
  []
  ())
