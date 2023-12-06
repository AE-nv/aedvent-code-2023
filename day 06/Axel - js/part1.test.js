import { expect, test } from "vitest";
import {
  parseInput,
  parseLine,
  part1,
  getAmountOfWiningOptionsForRace,
} from "./part1";

test("example case", () => {
  const input = ["Time:      7  15   30", "Distance:  9  40  200"];
  const output = part1(input);
  expect(output).toBe(288);
});

test("parseLine", () => {
  const input = "Time:      7  15   30";
  const output = parseLine(input);

  expect(output).toStrictEqual([7, 15, 30]);
});

test("parseInput", () => {
  const input = ["Time:      7  15   30", "Distance:  9  40  200"];
  const output = parseInput(input);
  expect(output.length).toBe(3);
  expect(output[0]).toStrictEqual({
    time: 7,
    distance: 9,
  });
});

test("winningOptionsForRace", () => {
  const race = {
    time: 7,
    distance: 9,
  };
  const output = getAmountOfWiningOptionsForRace(race);
  expect(output).toBe(4);
});
