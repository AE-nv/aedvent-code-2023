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

function getHorizontalMirrorLine(pattern, isReversed = false) {
  const temp = pattern;
  let amountOfRemovedLines = 0;
  while (temp.length > 1) {
    let part1;
    let part2;
    if (temp.length % 2 === 0) {
      part1 = temp.slice(0, temp.length / 2);
      part2 = temp.slice(temp.length / 2);
    } else {
      part1 = temp.slice(0, temp.length / 2);
      part2 = temp.slice(temp.length / 2 + 1);
    }
    const reversedPart2 = part2.reverse();
    if (part1.every((char, i) => char === reversedPart2[i])) {
      return Math.floor(temp.length / 2) + amountOfRemovedLines;
    }
    amountOfRemovedLines++;
    if (isReversed) {
      temp.pop();
    } else {
      temp.shift();
    }
  }
  return 0;
}

export function getHorizontalMirror(pattern) {
  return Math.max(
    getHorizontalMirrorLine(pattern, false),
    getHorizontalMirrorLine(pattern, true)
  );
}

export function getVerticalMirror(pattern) {
  const transposed = transpose(pattern);
  return Math.max(
    getHorizontalMirrorLine(transposed, false),
    getHorizontalMirrorLine(transposed, true)
  );
}

export function transpose(pattern) {
  return Object.keys(pattern[0])
    .map((c) => pattern.map((r) => r[c]))
    .map((line) => line.reverse())
    .map((line) => line.join(""));
}
