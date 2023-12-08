import { test, expect, describe } from "vitest";
import { getHandStrength, parseLine, part1 } from "./part1";

test("Example case", () => {
  const input = [
    "32T3K 765",
    "T55J5 684",
    "KK677 28",
    "KTJJT 220",
    "QQQJA 483",
  ];
  const output = part1(input);
  expect(output).toBe(6440);
});

test("parseLine", () => {
  const input = "32T3K 765";
  const output = parseLine(input);
  expect(output).toStrictEqual({
    hand: {
      2: 1,
      3: 2,
      4: 0,
      5: 0,
      6: 0,
      7: 0,
      8: 0,
      9: 0,
      T: 1,
      J: 0,
      Q: 0,
      K: 1,
      A: 0,
    },
    rawHand: "32T3K",
    bid: 765,
  });
});

describe("getHandStrength", () => {
  test("five of a kind", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
        9: 0,
        T: 5,
        J: 0,
        Q: 0,
        K: 0,
        A: 0,
      })
    ).toBe(7);
  });

  test("four of a kind", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
        9: 4,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 1,
      })
    ).toBe(6);
  });

  test("full house", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 3,
        4: 0,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
        9: 0,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 2,
      })
    ).toBe(5);
  });

  test("three of a kind", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 0,
        4: 3,
        5: 0,
        6: 1,
        7: 0,
        8: 0,
        9: 0,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 1,
      })
    ).toBe(4);
  });

  test("two pair", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 2,
        4: 2,
        5: 0,
        6: 0,
        7: 0,
        8: 0,
        9: 0,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 1,
      })
    ).toBe(3);
  });

  test("one pair", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 2,
        4: 1,
        5: 0,
        6: 1,
        7: 0,
        8: 0,
        9: 0,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 1,
      })
    ).toBe(2);
  });

  test("high card", () => {
    expect(
      getHandStrength({
        2: 0,
        3: 1,
        4: 1,
        5: 0,
        6: 0,
        7: 1,
        8: 1,
        9: 0,
        T: 0,
        J: 0,
        Q: 0,
        K: 0,
        A: 1,
      })
    ).toBe(1);
  });
});
