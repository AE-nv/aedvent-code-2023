package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
	"slices"
)

var NORTH, EAST, SOUTH, WEST = []int{0, -1}, []int{1, 0}, []int{0, 1}, []int{-1, 0}
var m = map[string]rune{}

func coord(x, y int) string {
	return fmt.Sprintf("%d,%d", x, y)
}

func followBeam(prevX, prevY int, dir []int, visited map[string]bool, e map[string]bool) {
	x, y := prevX+dir[0], prevY+dir[1]
	s, exists := m[coord(x, y)]
	if !exists || visited[coord(x, y)+coord(dir[0], dir[1])] {
		return
	}
	visited[coord(x, y)+coord(dir[0], dir[1])] = true
	e[coord(x, y)] = true
	switch s {
	case '.':
		followBeam(x, y, dir, visited, e)
	case '/':
		switch {
		case slices.Equal(dir, EAST):
			followBeam(x, y, NORTH, visited, e)
		case slices.Equal(dir, NORTH):
			followBeam(x, y, EAST, visited, e)
		case slices.Equal(dir, SOUTH):
			followBeam(x, y, WEST, visited, e)
		case slices.Equal(dir, WEST):
			followBeam(x, y, SOUTH, visited, e)
		}
	case '\\':
		switch {
		case slices.Equal(dir, EAST):
			followBeam(x, y, SOUTH, visited, e)
		case slices.Equal(dir, NORTH):
			followBeam(x, y, WEST, visited, e)
		case slices.Equal(dir, SOUTH):
			followBeam(x, y, EAST, visited, e)
		case slices.Equal(dir, WEST):
			followBeam(x, y, NORTH, visited, e)
		}
	case '-':
		if dir[1] == 0 {
			followBeam(x, y, dir, visited, e)
			return
		}
		followBeam(x, y, EAST, visited, e)
		followBeam(x, y, WEST, visited, e)
	case '|':
		if dir[0] == 0 {
			followBeam(x, y, dir, visited, e)
			return
		}
		followBeam(x, y, NORTH, visited, e)
		followBeam(x, y, SOUTH, visited, e)
	}
}

func sumEnergized(prevX, prevY int, dir []int) (sum int) {
	energized, visited := make(map[string]bool), make(map[string]bool)
	followBeam(prevX, prevY, dir, visited, energized)
	for _, isEnergized := range energized {
		if isEnergized {
			sum++
		}
	}
	return sum
}

func main() {
	lines := readLines()
	m = make(map[string]rune)
	for y, line := range lines {
		for x, c := range line {
			m[coord(x, y)] = c
		}
	}
	fmt.Println("Part 1", sumEnergized(-1, 0, EAST))
	part2 := 0.0
	for y := range lines {
		part2 = math.Max(part2, float64(sumEnergized(-1, y, EAST)))
		part2 = math.Max(part2, float64(sumEnergized(len(lines[0]), y, WEST)))
	}
	for x := range lines[0] {
		part2 = math.Max(part2, float64(sumEnergized(x, -1, SOUTH)))
		part2 = math.Max(part2, float64(sumEnergized(x, len(lines), NORTH)))
	}
	fmt.Println("Part 2", part2)

}

func readLines() (lines []string) {
	file, err := os.Open("input.txt")
	if err != nil {
		panic(err)
	}
	defer file.Close()
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		lines = append(lines, scanner.Text())
	}
	return lines
}
