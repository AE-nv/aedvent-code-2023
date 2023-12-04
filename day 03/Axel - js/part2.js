import fs from 'fs';

function readInput() {
  const file = fs.readFileSync('./input.txt');
  return file.toString('utf8').split('\n');
}

export function part2(input) {
  const numbers = [];
  const emptyLine = '.'.repeat(input[0].length);
  const nbLines = input.length;
  const nbChars = input[0].length;

  for (let i = 0; i < nbLines; i++) {
    const line = input[i];
    const previousLine = i !== 0 ? input[i - 1] : emptyLine;
    const nextLine = i !== nbLines - 1 ? input[i + 1] : emptyLine;

    let currentNumber = [];
    let isAdjacent = false;

    for (let j = 0; j < nbChars; j++) {
      const char = line[j];
      if (isNumber(char)) {
        currentNumber.push(char);
        if (isAdjacentToSymbol(j, line, previousLine, nextLine)) {
          isAdjacent = true;
        }
        const nextChar = j !== nbChars - 1 ? line[j + 1] : '.';
        if (!isNumber(nextChar)) {
          if (isAdjacent) {
            numbers.push(parseInt(currentNumber.join('')));
            isAdjacent = false;
          }
          currentNumber = [];
        }
      }
    }
  }
  return multiply(numbers);
}

export function getGearRatio(gearIndex, line, previousLines, nextLines) {
  if (line[gearIndex] !== '*') return 0;

  const previous = getSurroundingChars(gearIndex, previousLines[1]);
  const current = getSurroundingChars(gearIndex, line);
  const next = getSurroundingChars(gearIndex, nextLines[0]);

  return 1;
}

export function findPotentialGearIndexes(line) {
  let indexes = [];
  for (let i = 0; i < line.length; i++) {
    if (line[i] === '*') {
      indexes.push(i);
    }
  }
  return indexes;
}

export function isPartOfPartNumber(index, line, previousLine, nextLine) {
  const char = line[index];
  return (
    isNumber(char) && isAdjacentToSymbol(index, line, previousLine, nextLine)
  );
}

export function isAdjacentToSymbol(index, line, previousLine, nextLine) {
  const previous = getSurroundingChars(index, previousLine);
  const current = getSurroundingChars(index, line);
  const next = getSurroundingChars(index, nextLine);

  return (
    isAnyCharSymbol(previous) | isAnyCharSymbol(current) | isAnyCharSymbol(next)
  );
}

export function isAnyCharSymbol(array) {
  return array.some((char) => isSymbol(char));
}

export function getSurroundingChars(index, line) {
  const nextChar = index !== line.length - 1 ? line[index + 1] : '.';
  const previousChar = index !== 0 ? line[index - 1] : '.';
  const currentChar = line[index];
  return [previousChar, currentChar, nextChar];
}

function multiply(array) {
  return array.reduce((curr, acc) => (curr *= acc), 1);
}

export function isSymbol(char) {
  return char !== '.' && !isNumber(char);
}

function isNumber(char) {
  return /\d/.test(char);
}

const input = readInput();
const result = part2(input);
console.log(result);
