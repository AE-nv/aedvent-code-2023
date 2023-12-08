package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"slices"
)

func parseMap(lines []string) map[string][2]string {
	m := make(map[string][2]string)
	for _, line := range lines {
		parts := regexp.MustCompile(`[A-Z]{3}`).FindAllString(line, -1)
		m[parts[0]] = [2]string{parts[1], parts[2]}
	}
	return m
}

func calculateZCycle(pos string, m map[string][2]string, dirs string) (dist int) {
	for pos[len(pos)-1] != 'Z' {
		nextDir := 0
		if string(dirs[dist%len(dirs)]) == "R" {
			nextDir = 1
		}
		pos = m[pos][nextDir]
		dist++
	}
	return dist
}

func isDivisibleByAll(values []int, n int) bool {
	for _, v := range values {
		if n%v != 0 {
			return false
		}
	}
	return true
}

func findLeastCommonMultiple(values []int) int {
	maxCycle := slices.Max(values)
	for i := maxCycle; ; i += maxCycle {
		if isDivisibleByAll(values, i) {
			return i
		}
	}
}

func findPath(m map[string][2]string, dirs string) int {
	var positions []string
	for key := range m {
		if key[2] == 'A' {
			positions = append(positions, key)
		}
	}
	cycles := make([]int, 0)
	for _, p := range positions {
		cycles = append(cycles, calculateZCycle(p, m, dirs))
	}
	return findLeastCommonMultiple(cycles)
}

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	fmt.Println("Part 2", findPath(parseMap(lines[2:]), lines[0]))
}
