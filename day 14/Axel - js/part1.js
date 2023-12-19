export function part1(input) {
  const rotated = rotateInputNorth(input);
  return rotated
    .map((row, i) => {
      const amountOfRoundRocks = row.filter((char) => char === "O").length;
      const weight = rotated.length - i;
      return amountOfRoundRocks * weight;
    })
    .reduce((prev, curr) => prev + curr, 0);
}

function rotateInputNorth(input) {
  const transposedInput = transpose(input);
  const result = transposedInput.map((row) => processRow(row));
  return transpose(result);
}

export function processRow(column) {
  let result = [];
  for (let i = 0; i < column.length; i++) {
    const char = column[i];
    if (char === "#") {
      const emptySpaces = new Array(i - result.length).fill(".");
      result.push(...emptySpaces);
      result.push(char);
    } else if (char === "O") {
      result.push(char);
    }
  }
  const emptySpaces = new Array(column.length - result.length).fill(".");
  result.push(...emptySpaces);
  return result;
}

function transpose(pattern) {
  return Object.keys(pattern[0]).map((columnIndex) =>
    pattern.map((row) => row[columnIndex])
  );
}
