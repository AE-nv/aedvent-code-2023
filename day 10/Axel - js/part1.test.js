import { describe, test, expect } from "vitest";
import { part1 } from "./part1";

describe("Examples", () => {
  test("Example 1: simple loop", () => {
    const input = [".....", ".S-7.", ".|.|.", ".L-J.", "....."];
    expect(part1(input)).toBe(4);
  });

  test("Example 2: complex loop", () => {
    const input = ["..F7.", ".FJ|.", "SJ.L7", "|F--J", "LJ..."];
    expect(part1(input)).toBe(8);
  });
});
