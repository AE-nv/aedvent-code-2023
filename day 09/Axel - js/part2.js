export function part2(input) {
  return input
    .map((line) => line.split(" ").map((num) => parseInt(num)))
    .map((sequence) => getPreviousNum(sequence))
    .reduce((prev, curr) => prev + curr, 0);
}

export function getPreviousNum(sequence) {
  const derived = [sequence];
  let currentSequence = sequence;
  while (!currentSequence.every((val) => val === 0)) {
    let nextSequence = [];
    for (let i = 1; i < currentSequence.length; i++) {
      const curr = currentSequence[i];
      const prev = currentSequence[i - 1];
      const next = curr - prev;
      nextSequence.push(next);
    }
    derived.push(nextSequence);
    currentSequence = derived[derived.length - 1];
  }

  let previous = 0;
  for (let i = derived.length - 2; i >= 0; i--) {
    const currentSequence = derived[i];
    previous = currentSequence[0] - previous;
  }
  return previous;
}
