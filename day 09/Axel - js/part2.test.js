import { expect, test } from "vitest";
import { getPreviousNum } from "./part2";

test("getPreviousNum", () => {
  const input = [10, 13, 16, 21, 30, 45];
  expect(getPreviousNum(input)).toBe(5);
});
