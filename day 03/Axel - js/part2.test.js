import { expect, test, describe } from "vitest";
import {
  parseInput,
  extractNumbers,
  part2,
  extractSymbols,
  isPart,
  isGear,
} from "./part2";

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

  const output = part2(input);

  expect(output).toBe(467835);
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
  expect(output.potentialGears[1]).toStrictEqual([{ position: 3 }]);
});

test("isPart", () => {
  const number = { start: 0, end: 2, number: 467 };
  const surroundingSymbolLists = [[], [{ position: 3 }]];
  expect(isPart(number, surroundingSymbolLists)).toBeTruthy();
});

describe("isGear", () => {
  test("two adjacent parts", () => {
    const potentialGear = { position: 3 };
    const surroundingPartsLists = [
      [{ start: 0, number: 467, end: 2 }],
      [],
      [
        { start: 2, number: 35, end: 3 },
        { start: 6, number: 633, end: 8 },
      ],
    ];

    expect(isGear(potentialGear, surroundingPartsLists)).toBeTruthy();
  });

  test("less than two adjacent parts", () => {
    const potentialGear = { position: 3 };
    const surroundingPartsLists = [[], [{ start: 0, number: 617, end: 2 }], []];
    expect(isGear(potentialGear, surroundingPartsLists)).toBeFalsy();
  });
});
