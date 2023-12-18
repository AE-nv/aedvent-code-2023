package main

import (
	"fmt"
	"slices"
	"strconv"
	"strings"
)

type slot struct {
	l string
	f int
}

func main() {
	input := "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"
	commands := strings.Split(input, ",")
	part1, part2 := int32(0), 0
	for _, command := range commands {
		part1 += hash(command)
	}
	fmt.Println("Part 1:", part1)
	boxes := make(map[int32][]*slot, 256)
	for _, c := range commands {
		applyLens(boxes, c)
	}
	for h, b := range boxes {
		for i, l := range b {
			part2 += (int(h) + 1) * (i + 1) * l.f
		}
	}
	fmt.Println("Part 2:", part2)
}

func hash(s string) (h int32) {
	for _, c := range s {
		h += c
		h *= 17
		h = h % 256
	}
	return h
}

func applyLens(boxes map[int32][]*slot, c string) {
	label, focal := "", 0
	if c[len(c)-1] == '-' {
		label = c[:len(c)-1]
	} else {
		label, focal = c[:len(c)-2], toInt(c[len(c)-1:])
	}
	h := hash(label)
	box := boxes[h]
	s := slices.IndexFunc(box, func(s *slot) bool { return s.l == label })
	if focal == 0 {
		if s >= 0 {
			boxes[h] = append(box[:s], box[s+1:]...)
		}
	} else {
		if s >= 0 {
			boxes[h][s].f = focal
			return
		}
		boxes[h] = append(box, &slot{label, focal})
	}
}

func toInt(s string) int {
	result, err := strconv.Atoi(s)
	if err != nil {
		panic(err)
	}
	return result
}
