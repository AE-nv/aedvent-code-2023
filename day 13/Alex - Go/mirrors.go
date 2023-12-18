package main

import (
	"bufio"
	"fmt"
	"os"
	"slices"
	"strings"
)

func main() {
	mirrors := readMirrors()
	part1, part2 := 0, 0
	for _, m := range mirrors {
		part1 += mirrorScorePt1(m)
	}
	fmt.Println("Part 1", part1)
	for _, m := range mirrors {
		part2 += mirrorScorePt2(m)
	}
	fmt.Println("Part 2", part2)
}

func scoreReflections(hor, vert []int) int {
	if len(hor) == 1 {
		return (hor[0] + 1) * 100
	}
	if len(vert) == 1 {
		return vert[0] + 1
	}
	return 0
}

func mirrorScorePt1(m []string) int {
	return scoreReflections(reflections(m, nil, nil))
}

func column(lines []string, c int) string {
	var result strings.Builder
	for _, l := range lines {
		result.WriteByte(l[c])
	}
	return result.String()
}

func reflectsHorizontal(lines []string, row int) bool {
	for i := 0; i <= row; i++ {
		if row+i+1 >= len(lines) {
			return true
		}
		if lines[row-i] != lines[row+i+1] {
			return false
		}
	}
	return true
}

func reflectsVertical(lines []string, col int) bool {
	for i := 0; i <= col; i++ {
		if col+i+1 >= len(lines[0]) {
			return true
		}
		if column(lines, col-i) != column(lines, col+i+1) {
			return false
		}
	}
	return true
}

func swapLine(line string, x int) string {
	if line[x] == '#' {
		return line[:x] + string('.') + line[x+1:]
	} else {
		return line[:x] + string('#') + line[x+1:]
	}
}

func mirrorScorePt2(m []string) int {
	hor, vert := reflections(m, nil, nil)
	for y, line := range m {
		for x, _ := range line {
			swappedLine := swapLine(line, x)
			swappedMap := make([]string, y, len(m))
			copy(swappedMap, m[:y])
			swappedMap = append(swappedMap, swappedLine)
			swappedMap = append(swappedMap, m[y+1:]...)
			newHor, newVert := reflections(swappedMap, hor, vert)
			if len(newHor)+len(newVert) == 1 {
				return scoreReflections(newHor, newVert)
			}
		}
	}
	panic(fmt.Errorf("Could not find smudge in %s", m))
}

func reflections(m []string, excludingRows, excludingCols []int) (hor, vert []int) {
	for i := 0; i < len(m[0])-1; i++ {
		if !slices.Contains(excludingCols, i) && reflectsVertical(m, i) {
			vert = append(vert, i)
		}
	}
	for i := 0; i < len(m)-1; i++ {
		if !slices.Contains(excludingRows, i) && reflectsHorizontal(m, i) {
			hor = append(hor, i)
		}
	}
	return
}

func readMirrors() [][]string {
	var result [][]string
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return result
	}
	defer file.Close()
	scanner := bufio.NewScanner(file)
	var current []string
	for scanner.Scan() {
		line := scanner.Text()
		if strings.TrimSpace(line) == "" {
			result = append(result, current)
			current = nil
			continue
		}
		current = append(current, strings.TrimSpace(line))
	}
	if len(current) > 0 {
		result = append(result, current)
	}
	return result
}
