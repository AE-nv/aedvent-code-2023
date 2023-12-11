import { describe, expect, test } from "vitest";
import { getExpandedGalaxy, part2 } from "./part2";

describe("Example case", () => {
  test("Size 10", () => {
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
    expect(part2(input, 10)).toBe(1030);
  });

  test("Size 100", () => {
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
    expect(part2(input, 100)).toBe(8410);
  });
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
    [3, 7],
    [2, 5, 8],
  ];
  expect(getExpandedGalaxy(input)).toStrictEqual(expected);
});
