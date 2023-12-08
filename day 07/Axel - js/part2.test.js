import { test, expect, describe } from "vitest";
import { getHandStrength, parseLine, part2 } from "./part2";

test("Example case", () => {
  const input = [
    "32T3K 765",
    "T55J5 684",
    "KK677 28",
    "KTJJT 220",
    "QQQJA 483",
  ];
  const output = part2(input);
  expect(output).toBe(5905);
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
  describe("five of a kind", () => {
    test("no jokers", () => {
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

    test("some jokers", () => {
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
          T: 2,
          J: 3,
          Q: 0,
          K: 0,
          A: 0,
        })
      ).toBe(7);
    });
  });

  describe("four of a kind", () => {
    test("no jokers", () => {
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

    test("some jokers", () => {
      expect(
        getHandStrength({
          2: 0,
          3: 0,
          4: 0,
          5: 0,
          6: 0,
          7: 0,
          8: 0,
          9: 2,
          T: 0,
          J: 2,
          Q: 0,
          K: 0,
          A: 1,
        })
      ).toBe(6);
    });
  });

  describe("full house", () => {
    test("no jokers", () => {
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
    test("1 joker", () => {
      expect(
        getHandStrength({
          2: 0,
          3: 1,
          4: 0,
          5: 0,
          6: 0,
          7: 2,
          8: 0,
          9: 0,
          T: 0,
          J: 1,
          Q: 0,
          K: 0,
          A: 2,
        })
      ).toBe(5);
    });
  });

  describe("three of a kind", () => {
    test("no jokers", () => {
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

    test("1 joker", () => {
      expect(
        getHandStrength({
          2: 0,
          3: 0,
          4: 2,
          5: 0,
          6: 1,
          7: 0,
          8: 0,
          9: 0,
          T: 0,
          J: 1,
          Q: 0,
          K: 0,
          A: 1,
        })
      ).toBe(4);
    });

    test("more than 1 joker", () => {
      expect(
        getHandStrength({
          2: 0,
          3: 0,
          4: 1,
          5: 0,
          6: 1,
          7: 0,
          8: 0,
          9: 0,
          T: 0,
          J: 2,
          Q: 0,
          K: 0,
          A: 1,
        })
      ).toBe(4);
    });
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
