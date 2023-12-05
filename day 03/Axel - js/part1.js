export function part1(input) {
  const { numbers, symbols } = parseInput(input);
  const parts = [];
  for (let line = 0; line < input.length; line++) {
    numbers[line].forEach((number) => {
      const sliceStart = Math.max(line - 1, 0);
      const sliceEnd = Math.min(line + 2, input.length);
      const surroundingSymbolLists = symbols.slice(sliceStart, sliceEnd);
      if (isPart(number, surroundingSymbolLists)) {
        parts.push(number);
      }
    });
  }
  return parts
    .map((part) => part.number)
    .reduce((acc, curr) => (curr += acc), 0);
}

export function extractNumbers(line) {
  const numbers = [];

  let curr = [];
  let currStart;
  [...line].forEach((char, i, all) => {
    if (isNumber(char)) {
      if (!curr.length) {
        currStart = i;
      }
      curr.push(char);
      const nextChar = all[i + 1];
      if (!isNumber(nextChar)) {
        numbers.push({
          start: currStart,
          number: parseInt(curr.join("")),
          end: i,
        });
        curr = [];
      }
    }
  });
  return numbers;
}

export function extractSymbols(line) {
  return [...line]
    .map((char, i) => (isSymbol(char) ? { position: i } : undefined))
    .filter(Boolean);
}

export function parseInput(input) {
  return {
    numbers: input.map((line) => extractNumbers(line)),
    symbols: input.map((line) => extractSymbols(line)),
  };
}

function isNumber(char) {
  return /\d/.test(char);
}

function isSymbol(char) {
  return !isNumber(char) && char !== ".";
}

export function isPart(number, surroundingSymbolLists) {
  return surroundingSymbolLists
    .map((line) =>
      line.some((symbol) => isBetweenBoundary(number, symbol.position))
    )
    .reduce((prev, curr) => prev || curr, false);
}

function isBetweenBoundary(number, position) {
  return position >= number.start - 1 && position <= number.end + 1;
}
