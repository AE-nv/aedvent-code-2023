package main

import (
	"bufio"
	"fmt"
	"math"
	"os"
)

type galaxy struct {
	x, y int
}

func (g *galaxy) trueCoordinates(modifier int) (tx, ty int) {
	for ix := 0; ix < g.x; ix++ {
		if !xHasGalaxy[ix] {
			tx += modifier
		}
	}
	for iy := 0; iy < g.y; iy++ {
		if !yHasGalaxy[iy] {
			ty += modifier
		}
	}
	return tx + g.x, ty + g.y
}
func (g *galaxy) distance(z *galaxy, modifier int) int {
	gx, gy := g.trueCoordinates(modifier)
	zx, zy := z.trueCoordinates(modifier)
	return int(math.Abs(float64(gx-zx)) + math.Abs(float64(gy-zy)))
}

var xHasGalaxy, yHasGalaxy map[int]bool

func main() {
	xHasGalaxy, yHasGalaxy = make(map[int]bool), make(map[int]bool)
	lines := readLines()
	galaxies := make([]galaxy, 0)
	for y, line := range lines {
		for x, c := range line {
			if c == '#' {
				galaxies = append(galaxies, galaxy{x, y})
				xHasGalaxy[x], yHasGalaxy[y] = true, true
			}
		}
	}
	part1, part2 := 0, 0
	for i := 0; i < len(galaxies)-1; i++ {
		for j := i + 1; j < len(galaxies); j++ {
			part1 += galaxies[i].distance(&galaxies[j], 1)
		}
	}
	fmt.Println("Part 1", part1)
	for i := 0; i < len(galaxies)-1; i++ {
		for j := i + 1; j < len(galaxies); j++ {
			part2 += galaxies[i].distance(&galaxies[j], 1000000-1)
		}
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
