import { describe, expect, test } from "vitest";
import {
  calculateMapOutput,
  kebabCaseToCamelCase,
  parseInput,
  parseMapLine,
  parseSeeds,
  part1,
} from "./part1";

test("example input", () => {
  const input = [
    "seeds: 79 14 55 13",
    "",
    "seed-to-soil map:",
    "50 98 2",
    "52 50 48",
    "",
    "soil-to-fertilizer map:",
    "0 15 37",
    "37 52 2",
    "39 0 15",
    "",
    "fertilizer-to-water map:",
    "49 53 8",
    "0 11 42",
    "42 0 7",
    "57 7 4",
    "",
    "water-to-light map:",
    "88 18 7",
    "18 25 70",
    "",
    "light-to-temperature map:",
    "45 77 23",
    "81 45 19",
    "68 64 13",
    "",
    "temperature-to-humidity map:",
    "0 69 1",
    "1 0 69",
    "",
    "humidity-to-location map:",
    "60 56 37",
    "56 93 4",
  ];

  const result = part1(input);
  expect(result).toBe(35);
});

test("parse seeds", () => {
  const line = "seeds: 79 14 55 13";
  expect(parseSeeds(line)).toStrictEqual([79, 14, 55, 13]);
});

test("parse map line", () => {
  const line = "50 98 2";
  const result = parseMapLine(line);
  expect(result).toStrictEqual({
    destinationStart: 50,
    sourceStart: 98,
    range: 2,
  });
});

test("kebabCaseToCamelCase", () => {
  expect(kebabCaseToCamelCase("fertilizer-to-water")).toBe("fertilizerToWater");
});

test("parseInput", () => {
  const input = [
    "seeds: 79 14 55 13",
    "",
    "seed-to-soil map:",
    "50 98 2",
    "52 50 48",
    "",
    "soil-to-fertilizer map:",
    "0 15 37",
    "37 52 2",
    "39 0 15",
  ];
  const result = parseInput(input);
  expect(result.seeds).toStrictEqual([79, 14, 55, 13]);
  expect(result.seedToSoil).toStrictEqual([
    { destinationStart: 50, sourceStart: 98, range: 2 },
    { destinationStart: 52, sourceStart: 50, range: 48 },
  ]);
  expect(result.soilToFertilizer).toStrictEqual([
    { destinationStart: 0, sourceStart: 15, range: 37 },
    { destinationStart: 37, sourceStart: 52, range: 2 },
    { destinationStart: 39, sourceStart: 0, range: 15 },
  ]);
});

describe("calculate map output", () => {
  test("match", () => {
    const maps = [
      { destinationStart: 50, sourceStart: 98, range: 2 },
      { destinationStart: 52, sourceStart: 50, range: 48 },
    ];
    expect(calculateMapOutput(98, maps)).toBe(50);
  });

  test("no match", () => {
    const maps = [
      { destinationStart: 50, sourceStart: 98, range: 2 },
      { destinationStart: 52, sourceStart: 50, range: 48 },
    ];
    expect(calculateMapOutput(10, maps)).toBe(10);
  });
});
