export function part1(input) {
  const patterns = splitPattern(input);
  const verticalMirrorScore = patterns
    .map((p) => getVerticalMirror(p))
    .reduce((acc, curr) => acc + curr, 0);
  const horizontalMirrorScore = patterns
    .map((p) => getHorizontalMirror(p))
    .reduce((acc, curr) => acc + 100 * curr, 0);
  return verticalMirrorScore + horizontalMirrorScore;
}

function splitPattern(input) {
  const result = [];
  let temp = [];
  for (const line of input) {
    if (!line) {
      result.push(temp);
      temp = [];
    } else {
      temp.push(line);
    }
  }
  if (temp.length) {
    result.push(temp);
  }
  return result;
}

export function getHorizontalMirror(pattern) {
  for (let rowIndex = 0; rowIndex < pattern.length - 1; rowIndex++) {
    if (isIndexMirrorLine(pattern, rowIndex)) {
      return rowIndex + 1;
    }
  }
  return 0;
}

export function isIndexMirrorLine(pattern, i) {
  const nbRows = pattern.length;
  const nbCols = pattern[0].length;
  for (let colIndex = 0; colIndex < nbCols; colIndex++) {
    for (let rowIndex = 0; rowIndex < nbRows; rowIndex++) {
      const mirroredRowIndex = i * 2 + 1 - rowIndex;
      if (mirroredRowIndex < 0 || mirroredRowIndex >= nbRows) {
        continue;
      }
      if (pattern[rowIndex][colIndex] !== pattern[mirroredRowIndex][colIndex]) {
        return false;
      }
    }
  }
  return true;
}

export function getVerticalMirror(pattern) {
  const transposed = transpose(pattern);
  return getHorizontalMirror(transposed);
}

export function transpose(pattern) {
  return Object.keys(pattern[0])
    .map((columnIndex) => pattern.map((row) => row[columnIndex]))
    .map((line) => line.join(""));
}
