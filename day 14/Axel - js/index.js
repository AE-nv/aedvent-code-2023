import fs from "fs";
// import { part2 } from "./part2.js";
import { part1 } from "./part1.js";

function readInput() {
  const file = fs.readFileSync("./input");
  return file.toString("utf8").split("\n");
}

function doPart1() {
  const input = readInput();

  const resultPart1 = part1(input);
  console.log(`part1: ${resultPart1}`);
}
doPart1();

// function doPart2() {
// const input = readInput();

// const resultPart2 = part2(input);
// console.log(`part2: ${resultPart2}`);
// }

// doPart2();
