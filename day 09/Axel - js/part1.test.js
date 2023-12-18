import { expect, test } from "vitest";
import { getNextNum, part1 } from "./part1";

test("Example case", () => {
  const input = ["0 3 6 9 12 15", "1 3 6 10 15 21", "10 13 16 21 30 45"];
  const output = part1(input);
  expect(output).toBe(114);
});

test("getNextNum", () => {
  const input = [0, 3, 6, 9, 12, 15];
  expect(getNextNum(input)).toBe(18);
});
