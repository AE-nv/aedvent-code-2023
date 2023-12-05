export function part2(input) {
  const { numbers, symbols, potentialGears } = parseInput(input);
  const parts = extractParts(numbers, symbols);
  return potentialGears
    .flatMap((potentialGearsOfLine, line) =>
      potentialGearsOfLine.map((potentialGear) => {
        const sliceStart = Math.max(line - 1, 0);
        const sliceEnd = Math.min(line + 2, potentialGears.length);
        const surroundingPartsLists = parts.slice(sliceStart, sliceEnd);
        if (isGear(potentialGear, surroundingPartsLists)) {
          return getGearRatio(potentialGear, surroundingPartsLists);
        } else {
          return 0;
        }
      })
    )
    .reduce((acc, curr) => (curr += acc), 0);
}

export function extractParts(numbers, symbols) {
  return numbers.map((numbersOfLine, line) =>
    numbersOfLine.filter((number) => {
      const sliceStart = Math.max(line - 1, 0);
      const sliceEnd = Math.min(line + 2, numbers.length);
      const surroundingSymbolLists = symbols.slice(sliceStart, sliceEnd);
      return isPart(number, surroundingSymbolLists);
    })
  );
}

export function sumGearRatios(parts, potentialGears) {
  return potentialGears
    .flatMap((potentialGearsOfLine, line) =>
      potentialGearsOfLine.map((potentialGear) => {
        const sliceStart = Math.max(line - 1, 0);
        const sliceEnd = Math.min(line + 2, potentialGears.length);
        const surroundingPartsLists = parts.slice(sliceStart, sliceEnd);
        if (isGear(potentialGear, surroundingPartsLists)) {
          return getGearRatio(potentialGear, surroundingPartsLists);
        } else {
          return 0;
        }
      })
    )
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

export function extractPotentialGears(line) {
  return [...line]
    .map((char, i) => (isGearSymbol(char) ? { position: i } : undefined))
    .filter(Boolean);
}

export function parseInput(input) {
  return {
    numbers: input.map((line) => extractNumbers(line)),
    symbols: input.map((line) => extractSymbols(line)),
    potentialGears: input.map((line) => extractPotentialGears(line)),
  };
}

function isNumber(char) {
  return /\d/.test(char);
}

function isSymbol(char) {
  return !isNumber(char) && char !== ".";
}

function isGearSymbol(char) {
  return char === "*";
}

export function isPart(number, surroundingSymbolLists) {
  return surroundingSymbolLists
    .map((line) =>
      line.some((symbol) =>
        isBetweenBoundary(number.start, number.end, symbol.position)
      )
    )
    .reduce((prev, curr) => prev || curr, false);
}

function isBetweenBoundary(start, end, position) {
  return position >= start - 1 && position <= end + 1;
}

export function isGear(potentialGear, surroundingPartsLists) {
  const adjacentParts = getPartsAdjacentToPotentialGear(
    potentialGear,
    surroundingPartsLists
  );
  return adjacentParts.length === 2;
}

function getPartsAdjacentToPotentialGear(potentialGear, surroundingPartsLists) {
  return surroundingPartsLists.flatMap((line) =>
    line.filter((part) =>
      isBetweenBoundary(part.start, part.end, potentialGear.position)
    )
  );
}

export function getGearRatio(gear, surroundingPartsLists) {
  return getPartsAdjacentToPotentialGear(gear, surroundingPartsLists)
    .map((part) => part.number)
    .reduce((acc, curr) => (acc *= curr), 1);
}
