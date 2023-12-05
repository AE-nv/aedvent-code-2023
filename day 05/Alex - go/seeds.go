package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func seeds_p1(str string) (seeds []int) {
	parts := strings.Split(str, " ")
	for _, c := range parts {
		seeds = append(seeds, parseInt(c))
	}
	return seeds
}

func seeds_p2(parts []string) (seeds []int) {
	for r := 0; r < parseInt(parts[1]); r++ {
		seeds = append(seeds, parseInt(parts[0])+r)
	}
	return seeds
}

type mapping struct {
	start int
	r     int
	dest  int
}

func parsemap(lines []string) (mappings []mapping) {
	for _, line := range lines {
		parts := strings.Split(line, " ")
		mappings = append(mappings, mapping{parseInt(parts[1]), parseInt(parts[2]), parseInt(parts[0])})
	}
	return mappings
}

func readmaps() ([]mapping, []mapping, []mapping, []mapping, []mapping, []mapping, []mapping) {
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		os.Exit(1)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	var lines = make([]string, 0)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}

	seedToSoil := parsemap(lines[3:13])
	soilToFertilizer := parsemap(lines[15:24])
	fertilizerToWater := parsemap(lines[26:68])
	waterToLight := parsemap(lines[70:114])
	lightToTemp := parsemap(lines[116:162])
	tempToHum := parsemap(lines[164:191])
	humToLoc := parsemap(lines[193:221])

	return seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHum, humToLoc
}

func convertValue(val int, mappings []mapping) int {
	for _, m := range mappings {
		if m.start <= val && val < m.start+m.r {
			return m.dest + (val - m.start)
		}
	}
	return val
}

func convertList(vals []int, mappings []mapping) []int {
	var result []int
	for _, v := range vals {
		result = append(result, convertValue(v, mappings))
	}
	return result
}

func main() {
	ch := make(chan int)
	str := "2019933646 2719986 2982244904 337763798 445440 255553492 1676917594 196488200 3863266382 36104375 1385433279 178385087 2169075746 171590090 572674563 5944769 835041333 194256900 664827176 42427020"
	go run(seeds_p1(str), ch)
	fmt.Println("Part 1", <-ch)

	minValue := 999999999999999999
	workers := 8
	parts := strings.Split(str, " ")
	for i := 0; i < len(parts); i += 2 {
		seeds2 := seeds_p2(parts[i : i+2])
		for i := 0; i < workers; i++ {
			blocks := len(seeds2) / workers
			go run(seeds2[i*blocks:min((i+1)*blocks, len(seeds2))], ch)
		}
		for i := 0; i < workers; i++ {
			received := <-ch
			minValue = min(minValue, received)
		}
	}
	fmt.Println("Part 2", minValue)
}

func run(seeds []int, ch chan<- int) {
	soilToSeed, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHum, humToLoc := readmaps()
	soils := convertList(seeds, soilToSeed)
	fertilizers := convertList(soils, soilToFertilizer)
	waters := convertList(fertilizers, fertilizerToWater)
	lights := convertList(waters, waterToLight)
	temps := convertList(lights, lightToTemp)
	hum := convertList(temps, tempToHum)
	locs := convertList(hum, humToLoc)

	minValue := locs[0]
	for _, val := range locs {
		if val < minValue {
			minValue = val
		}
	}
	ch <- minValue
}

func parseInt(str string) int {
	val, err := strconv.Atoi(str)
	if err != nil {
		panic(err)
	}
	return val
}
