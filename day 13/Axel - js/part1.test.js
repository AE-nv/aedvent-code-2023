import { describe, expect, test } from "vitest";
import { getHorizontalMirror, part1, getVerticalMirror } from "./part1";

test("Example case", () => {
  const input = [
    "#.##..##.",
    "..#.##.#.",
    "##......#",
    "##......#",
    "..#.##.#.",
    "..##..##.",
    "#.#.##.#.",
    "",
    "#...##..#",
    "#....#..#",
    "..##..###",
    "#####.##.",
    "#####.##.",
    "..##..###",
    "#....#..#",
  ];

  expect(part1(input)).toBe(405);
});

describe("getVerticalMirror", () => {
  test("is mirrored", () => {
    const input = [
      "#.##..##.",
      "..#.##.#.",
      "##......#",
      "##......#",
      "..#.##.#.",
      "..##..##.",
      "#.#.##.#.",
    ];
    expect(getVerticalMirror(input)).toBe(5);
  });

  test("not mirrored", () => {
    const input = [
      "#...##..#",
      "#....#..#",
      "..##..###",
      "#####.##.",
      "#####.##.",
      "..##..###",
      "#....#..#",
    ];
    expect(getVerticalMirror(input)).toBe(0);
  });
});

describe("getHorizontalMirror", () => {
  test("is mirrored", () => {
    const input = [
      "#...##..#",
      "#....#..#",
      "..##..###",
      "#####.##.",
      "#####.##.",
      "..##..###",
      "#....#..#",
    ];
    expect(getHorizontalMirror(input)).toBe(4);
  });

  test("not mirrored", () => {
    const input = [
      "#.##..##.",
      "..#.##.#.",
      "##......#",
      "##......#",
      "..#.##.#.",
      "..##..##.",
      "#.#.##.#.",
    ];
    expect(getHorizontalMirror(input)).toBe(0);
  });
});
