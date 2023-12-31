import { expect, test } from "vitest";
import { getExpandedGalaxy, part1 } from "./part1";

test("Example case", () => {
  const input = [
    "...#......",
    ".......#..",
    "#.........",
    "..........",
    "......#...",
    ".#........",
    ".........#",
    "..........",
    ".......#..",
    "#...#.....",
  ];
  expect(part1(input)).toBe(374);
});

test("getExpandedGalaxy", () => {
  const input = [
    "...#......",
    ".......#..",
    "#.........",
    "..........",
    "......#...",
    ".#........",
    ".........#",
    "..........",
    ".......#..",
    "#...#.....",
  ];

  const expected = [
    "....#........",
    ".........#...",
    "#............",
    ".............",
    ".............",
    "........#....",
    ".#...........",
    "............#",
    ".............",
    ".............",
    ".........#...",
    "#....#.......",
  ];
  expect(getExpandedGalaxy(input)).toStrictEqual(expected);
});
