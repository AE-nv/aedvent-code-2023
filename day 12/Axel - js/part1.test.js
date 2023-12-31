import { expect, test } from "vitest";
import { part1 } from "./part1";

test("Example case", () => {
  const input = [
    "???.### 1,1,3",
    ".??..??...?##. 1,1,3",
    "?#?#?#?#?#?#?#? 1,3,1,6",
    "????.#...#... 4,1,1",
    "????.######..#####. 1,6,5",
    "?###???????? 3,2,1",
  ];
  expect(part1(input)).toBe(21);
});
