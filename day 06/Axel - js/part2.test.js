import { expect, test } from "vitest";
import {
  parseInput,
  parseLine,
  part2,
  getAmountOfWiningOptionsForRace,
} from "./part2";

test("example case", () => {
  const input = ["Time:      7  15   30", "Distance:  9  40  200"];
  const output = part2(input);
  expect(output).toBe(71503);
});

test("parseLine", () => {
  const input = "Time:      7  15   30";
  const output = parseLine(input);

  expect(output).toBe(71530);
});

test("parseInput", () => {
  const input = ["Time:      7  15   30", "Distance:  9  40  200"];
  const output = parseInput(input);
  expect(output).toStrictEqual({
    time: 71530,
    distance: 940200,
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
