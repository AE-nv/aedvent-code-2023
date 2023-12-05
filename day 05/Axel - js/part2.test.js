import { describe, expect, test } from "vitest";
import {
  calculateLocation,
  calculateMapOutput,
  kebabCaseToCamelCase,
  parseInput,
  parseMapLine,
  parseSeeds,
  part2,
} from "./part2";

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

  const result = part2(input);
  expect(result).toBe(46);
});

test("parse seeds", () => {
  const line = "seeds: 79 14 55 13";
  expect(parseSeeds(line)).toStrictEqual([
    { start: 79, end: 92 },
    { start: 55, end: 67 },
  ]);
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
  expect(result.seeds).toStrictEqual([
    { start: 79, end: 92 },
    { start: 55, end: 67 },
  ]);
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

test("calculate location", () => {
  const maps = {
    seedToSoil: [
      { destinationStart: 50, sourceStart: 98, range: 2 },
      { destinationStart: 52, sourceStart: 50, range: 48 },
    ],
    soilToFertilizer: [
      { destinationStart: 0, sourceStart: 15, range: 37 },
      { destinationStart: 37, sourceStart: 52, range: 2 },
      { destinationStart: 39, sourceStart: 0, range: 15 },
    ],
    fertilizerToWater: [
      { destinationStart: 49, sourceStart: 53, range: 8 },
      { destinationStart: 0, sourceStart: 11, range: 42 },
      { destinationStart: 42, sourceStart: 0, range: 7 },
      { destinationStart: 57, sourceStart: 7, range: 4 },
    ],
    waterToLight: [
      { destinationStart: 88, sourceStart: 18, range: 7 },
      { destinationStart: 18, sourceStart: 25, range: 70 },
    ],
    lightToTemperature: [
      { destinationStart: 45, sourceStart: 77, range: 23 },
      { destinationStart: 81, sourceStart: 45, range: 19 },
      { destinationStart: 68, sourceStart: 64, range: 13 },
    ],
    temperatureToHumidity: [
      { destinationStart: 0, sourceStart: 69, range: 1 },
      { destinationStart: 1, sourceStart: 0, range: 69 },
    ],
    humidityToLocation: [
      { destinationStart: 60, sourceStart: 56, range: 37 },
      { destinationStart: 56, sourceStart: 93, range: 4 },
    ],
  };

  expect(calculateLocation(79, maps)).toBe(82);
  expect(calculateLocation(14, maps)).toBe(43);
  expect(calculateLocation(55, maps)).toBe(86);
  expect(calculateLocation(13, maps)).toBe(35);
});
