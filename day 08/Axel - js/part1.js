export function part1(unparsedInput) {
  const input = parseInput(unparsedInput);
  let steps = 0;
  let current = "AAA";
  while (current !== "ZZZ") {
    const i = steps % input.sequence.length;
    const direction = input.sequence.charAt(i);
    const currentNode = input.nodes[current];
    current = currentNode[direction];
    steps += 1;
  }
  return steps;
}

export function parseInput(input) {
  const sequence = input.shift();
  input.shift();

  let nodes = {};
  input
    .map((line) => parseNodeMap(line))
    .forEach((element) => {
      nodes[element.current] = { L: element.L, R: element.R };
    });
  return { sequence, nodes };
}

function parseNodeMap(line) {
  const [current, map] = line.split(" = ");
  const [L, R] = map.split(", ");
  return { current, L: L.slice(1), R: R.slice(0, -1) };
}
