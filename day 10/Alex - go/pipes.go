package main

import (
	"bufio"
	"container/ring"
	"fmt"
	"os"
	"slices"
)

var m map[string]*ring.Ring

func coord(x, y int) string {
	return fmt.Sprintf("%d,%d", x, y)
}
func getNextDir(x, y int) (int, int) {
	c := m[coord(x, y)]
	if e, ok := m[coord(x+1, y)]; ok && slices.Contains([]rune{'S', 'F', '-', 'L'}, c.Value.(rune)) && c.Prev() != e && slices.Contains([]rune{'S', '-', 'J', '7'}, e.Value.(rune)) {
		return x + 1, y
	} else if s, ok := m[coord(x, y+1)]; ok && slices.Contains([]rune{'S', 'F', '|', '7'}, c.Value.(rune)) && c.Prev() != s && slices.Contains([]rune{'S', '|', 'L', 'J'}, s.Value.(rune)) {
		return x, y + 1
	} else if w, ok := m[coord(x-1, y)]; ok && slices.Contains([]rune{'S', '7', '-', 'J'}, c.Value.(rune)) && m[coord(x, y)].Prev() != w && slices.Contains([]rune{'S', '-', 'L', 'F'}, w.Value.(rune)) {
		return x - 1, y
	} else if n, ok := m[coord(x, y-1)]; ok && slices.Contains([]rune{'S', '|', 'J', 'L'}, c.Value.(rune)) && c.Prev() != n && slices.Contains([]rune{'S', '|', 'F', '7'}, n.Value.(rune)) {
		return x, y - 1
	}
	panic(fmt.Errorf("no next direction found for %d,%d", x, y))
}

func connectAnimalLoop(x, y int) {
	current := m[coord(x, y)]
	nextX, nextY := getNextDir(x, y)
	nextPos := m[coord(nextX, nextY)]
	if current.Next() != nextPos {
		current.Link(nextPos)
		connectAnimalLoop(nextX, nextY)
	}
}

func main() {
	m = make(map[string]*ring.Ring)
	lines := readLines()
	startX, startY := 0, 0
	for y, line := range lines {
		for x, c := range line {
			if c == '.' {
				continue
			}
			r := ring.New(1)
			r.Value = c
			m[coord(x, y)] = r
			if c == 'S' {
				startX, startY = x, y
			}
		}
	}
	connectAnimalLoop(startX, startY)
	animalRing := m[coord(startX, startY)]
	fmt.Println("part 1", animalRing.Len()/2)
	innerTiles := 0
	for y, line := range lines {
		insideLoop := false
		for x, c := range line {
			if pos := m[coord(x, y)]; pos != nil && pos.Next() != pos { // Only connected the animal loop
				if c == '7' || c == 'F' || (c == '|') { // Disregard other intersections to avoid double creases
					insideLoop = !insideLoop
				}
			} else if insideLoop {
				innerTiles++
			}
		}
	}
	fmt.Println("part 2", innerTiles)
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
