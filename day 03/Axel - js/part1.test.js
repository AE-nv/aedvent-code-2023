import { expect, test, describe } from "vitest";
import {
  parseInput,
  extractNumbers,
  part1,
  extractSymbols,
  isPart,
} from "./part1";

test("example case", () => {
  const input = [
    "467..114..",
    "...*......",
    "..35..633.",
    "......#...",
    "617*......",
    ".....+.58.",
    "..592.....",
    "......755.",
    "...$.*....",
    ".664.598..",
  ];

  const output = part1(input);

  expect(output).toBe(4361);
});

describe("extractNumbers", () => {
  test("no numbers", () => {
    const line = "..*......";
    const result = extractNumbers(line);
    expect(result).toStrictEqual([]);
  });

  test("numbers", () => {
    const line = "467..114..";
    const result = extractNumbers(line);
    expect(result).toStrictEqual([
      { start: 0, end: 2, number: 467 },
      { start: 5, end: 7, number: 114 },
    ]);
  });
});

describe("extractSymbols", () => {
  test("no symbols", () => {
    const line = "467..114..";
    const result = extractSymbols(line);
    expect(result).toStrictEqual([]);
  });

  test("symbols", () => {
    const line = "..*......";
    const result = extractSymbols(line);
    expect(result).toStrictEqual([{ position: 2 }]);
  });
});

test("parseInput", () => {
  const input = [
    "467..114..",
    "...*......",
    "..35..633.",
    "......#...",
    "617*......",
    ".....+.58.",
    "..592.....",
    "......755.",
    "...$.*....",
    ".664.598..",
  ];

  const output = parseInput(input);
  expect(output.numbers.length).toBe(10);
  expect(output.numbers[0]).toStrictEqual([
    { start: 0, end: 2, number: 467 },
    { start: 5, end: 7, number: 114 },
  ]);
  expect(output.symbols[1]).toStrictEqual([{ position: 3 }]);
});

test("isPart", () => {
  const number = { start: 0, end: 2, number: 467 };
  const surroundingSymbolLists = [[], [{ position: 3 }]];
  expect(isPart(number, surroundingSymbolLists)).toBeTruthy();
});
