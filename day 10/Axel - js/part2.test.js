import { describe, test, expect } from "vitest";
import { isGroundWithinMainLoop, part2 } from "./part2";

describe("Examples", () => {
  test("Example 1: simple loop", () => {
    const input = [
      "...........",
      ".S-------7.",
      ".|F-----7|.",
      ".||.....||.",
      ".||.....||.",
      ".|L-7.F-J|.",
      ".|..|.|..|.",
      ".L--J.L--J.",
      "...........",
    ];
    expect(part2(input)).toBe(4);
  });
  test("Example 2: simple loop, connected between pipes", () => {
    const input = [
      "..........",
      ".S------7.",
      ".|F----7|.",
      ".||....||.",
      ".||....||.",
      ".|L-7F-J|.",
      ".|..||..|.",
      ".L--JL--J.",
      "..........",
    ];
    expect(part2(input)).toBe(4);
  });

  test("Example 3: complex loop", () => {
    const input = [
      "FF7FSF7F7F7F7F7F---7",
      "L|LJ||||||||||||F--J",
      "FL-7LJLJ||||||LJL-77",
      "F--JF--7||LJLJIF7FJ-",
      "L---JF-JLJIIIIFJLJJ7",
      "|F|F-JF---7IIIL7L|7|",
      "|FFJF7L7F-JF7IIL---7",
      "7-L-JL7||F7|L7F-7F7|",
      "L.L7LFJ|||||FJL7||LJ",
      "L7JLJL-JLJLJL--JLJ.L",
    ];
    expect(part2(input)).toBe(10);
  });
});

describe("isGroundWithinMainLoop", () => {
  test("Not a ground tile", () => {
    const input = getMockInput();
    const mainLoop = getMockLoop();

    expect(isGroundWithinMainLoop([1, 1], input, mainLoop)).toBeFalsy();
  });

  test("Outside of bounding box", () => {
    const input = getMockInput();
    const mainLoop = getMockLoop();

    expect(isGroundWithinMainLoop([0, 0], input, mainLoop)).toBeFalsy();
  });

  test("Inside of bounding box but not within loop", () => {
    const input = getMockInput();
    const mainLoop = getMockLoop();

    expect(isGroundWithinMainLoop([4, 3], input, mainLoop)).toBeFalsy();
  });

  test("Inside of loop", () => {
    const input = getMockInput();
    const mainLoop = getMockLoop();

    expect(isGroundWithinMainLoop([2, 6], input, mainLoop)).toBeTruthy();
  });
});

function getMockInput() {
  return [
    "...........",
    ".S-------7.",
    ".|F-----7|.",
    ".||.....||.",
    ".||.....||.",
    ".|L-7.F-J|.",
    ".|..|.|..|.",
    ".L--J.L--J.",
    "...........",
  ];
}

function getMockLoop() {
  return [
    {
      location: [1, 2],
      previousLocation: [1, 1],
      nextLocation: [1, 3],
    },
    {
      location: [1, 3],
      previousLocation: [1, 2],
      nextLocation: [1, 4],
    },
    {
      location: [1, 4],
      previousLocation: [1, 3],
      nextLocation: [1, 5],
    },
    {
      location: [1, 5],
      previousLocation: [1, 4],
      nextLocation: [1, 6],
    },
    {
      location: [1, 6],
      previousLocation: [1, 5],
      nextLocation: [1, 7],
    },
    {
      location: [1, 7],
      previousLocation: [1, 6],
      nextLocation: [1, 8],
    },
    {
      location: [1, 8],
      previousLocation: [1, 7],
      nextLocation: [1, 9],
    },
    {
      location: [1, 9],
      previousLocation: [1, 8],
      nextLocation: [2, 9],
    },
    {
      location: [2, 9],
      previousLocation: [1, 9],
      nextLocation: [3, 9],
    },
    {
      location: [3, 9],
      previousLocation: [2, 9],
      nextLocation: [4, 9],
    },
    {
      location: [4, 9],
      previousLocation: [3, 9],
      nextLocation: [5, 9],
    },
    {
      location: [5, 9],
      previousLocation: [4, 9],
      nextLocation: [6, 9],
    },
    {
      location: [6, 9],
      previousLocation: [5, 9],
      nextLocation: [7, 9],
    },
    {
      location: [7, 9],
      previousLocation: [6, 9],
      nextLocation: [7, 8],
    },
    {
      location: [7, 8],
      previousLocation: [7, 9],
      nextLocation: [7, 7],
    },
    {
      location: [7, 7],
      previousLocation: [7, 8],
      nextLocation: [7, 6],
    },
    {
      location: [7, 6],
      previousLocation: [7, 7],
      nextLocation: [6, 6],
    },
    {
      location: [6, 6],
      previousLocation: [7, 6],
      nextLocation: [5, 6],
    },
    {
      location: [5, 6],
      previousLocation: [6, 6],
      nextLocation: [5, 7],
    },
    {
      location: [5, 7],
      previousLocation: [5, 6],
      nextLocation: [5, 8],
    },
    {
      location: [5, 8],
      previousLocation: [5, 7],
      nextLocation: [4, 8],
    },
    {
      location: [4, 8],
      previousLocation: [5, 8],
      nextLocation: [3, 8],
    },
    {
      location: [3, 8],
      previousLocation: [4, 8],
      nextLocation: [2, 8],
    },
    {
      location: [2, 8],
      previousLocation: [3, 8],
      nextLocation: [2, 7],
    },
    {
      location: [2, 7],
      previousLocation: [2, 8],
      nextLocation: [2, 6],
    },
    {
      location: [2, 6],
      previousLocation: [2, 7],
      nextLocation: [2, 5],
    },
    {
      location: [2, 5],
      previousLocation: [2, 6],
      nextLocation: [2, 4],
    },
    {
      location: [2, 4],
      previousLocation: [2, 5],
      nextLocation: [2, 3],
    },
    {
      location: [2, 3],
      previousLocation: [2, 4],
      nextLocation: [2, 2],
    },
    {
      location: [2, 2],
      previousLocation: [2, 3],
      nextLocation: [3, 2],
    },
    {
      location: [3, 2],
      previousLocation: [2, 2],
      nextLocation: [4, 2],
    },
    {
      location: [4, 2],
      previousLocation: [3, 2],
      nextLocation: [5, 2],
    },
    {
      location: [5, 2],
      previousLocation: [4, 2],
      nextLocation: [5, 3],
    },
    {
      location: [5, 3],
      previousLocation: [5, 2],
      nextLocation: [5, 4],
    },
    {
      location: [5, 4],
      previousLocation: [5, 3],
      nextLocation: [6, 4],
    },
    {
      location: [6, 4],
      previousLocation: [5, 4],
      nextLocation: [7, 4],
    },
    {
      location: [7, 4],
      previousLocation: [6, 4],
      nextLocation: [7, 3],
    },
    {
      location: [7, 3],
      previousLocation: [7, 4],
      nextLocation: [7, 2],
    },
    {
      location: [7, 2],
      previousLocation: [7, 3],
      nextLocation: [7, 1],
    },
    {
      location: [7, 1],
      previousLocation: [7, 2],
      nextLocation: [6, 1],
    },
    {
      location: [6, 1],
      previousLocation: [7, 1],
      nextLocation: [5, 1],
    },
    {
      location: [5, 1],
      previousLocation: [6, 1],
      nextLocation: [4, 1],
    },
    {
      location: [4, 1],
      previousLocation: [5, 1],
      nextLocation: [3, 1],
    },
    {
      location: [3, 1],
      previousLocation: [4, 1],
      nextLocation: [2, 1],
    },
    {
      location: [2, 1],
      previousLocation: [3, 1],
      nextLocation: [1, 1],
    },
  ];
}
