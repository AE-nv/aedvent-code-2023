import { expect, test } from "vitest";
import { part1, processRow } from "./part1";

test("Example case", () => {
  const input = [
    "O....#....",
    "O.OO#....#",
    ".....##...",
    "OO.#O....O",
    ".O.....O#.",
    "O.#..O.#.#",
    "..O..#O..O",
    ".......O..",
    "#....###..",
    "#OO..#....",
  ];

  expect(part1(input)).toBe(136);
});

test("processRow", () => {
  const input = "#.#..O#.##".split("");
  expect(processRow(input).join("")).toBe("#.#O..#.##");
});
