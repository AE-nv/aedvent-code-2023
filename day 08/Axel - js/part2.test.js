import { expect, test } from "vitest";
import { parseInput, part2 } from "./part2";

test("Example case", () => {
  const input = [
    "LR",
    "",
    "11A = (11B, XXX)",
    "11B = (XXX, 11Z)",
    "11Z = (11B, XXX)",
    "22A = (22B, XXX)",
    "22B = (22C, 22C)",
    "22C = (22Z, 22Z)",
    "22Z = (22B, 22B)",
    "XXX = (XXX, XXX)",
  ];
  const output = part2(input);
  expect(output).toBe(6);
});

test("parseInput", () => {
  const input = [
    "LLR",
    "",
    "AAA = (BBB, BBB)",
    "BBB = (AAA, ZZZ)",
    "ZZZ = (ZZZ, ZZZ)",
  ];
  const output = parseInput(input);

  expect(output.sequence).toStrictEqual("LLR");
  expect(output.nodes).toStrictEqual({
    AAA: {
      L: "BBB",
      R: "BBB",
      isStartNode: true,
      isEndNode: false,
    },
    BBB: {
      L: "AAA",
      R: "ZZZ",
      isStartNode: false,
      isEndNode: false,
    },
    ZZZ: {
      L: "ZZZ",
      R: "ZZZ",
      isStartNode: false,
      isEndNode: true,
    },
  });
});
