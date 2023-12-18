import { describe, expect, test } from "vitest";
import { parseInput, part1 } from "./part1";

describe("Example cases", () => {
  test("Case 1", () => {
    const input = [
      "RL",
      "",
      "AAA = (BBB, CCC)",
      "BBB = (DDD, EEE)",
      "CCC = (ZZZ, GGG)",
      "DDD = (DDD, DDD)",
      "EEE = (EEE, EEE)",
      "GGG = (GGG, GGG)",
      "ZZZ = (ZZZ, ZZZ)",
    ];
    const output = part1(input);
    expect(output).toBe(2);
  });

  test("Case 1", () => {
    const input = [
      "LLR",
      "",
      "AAA = (BBB, BBB)",
      "BBB = (AAA, ZZZ)",
      "ZZZ = (ZZZ, ZZZ)",
    ];
    const output = part1(input);
    expect(output).toBe(6);
  });
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
    },
    BBB: {
      L: "AAA",
      R: "ZZZ",
    },
    ZZZ: {
      L: "ZZZ",
      R: "ZZZ",
    },
  });
});
