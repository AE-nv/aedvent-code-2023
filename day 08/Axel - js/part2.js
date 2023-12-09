export function part2(unparsedInput) {
  const input = parseInput(unparsedInput);
  let startNodes = [];
  Object.values(input.nodes).forEach((node) => {
    if (node.isStartNode) {
      startNodes.push(node);
    }
  });

  return getAmountOfSteps(startNodes, input);
}

function getAmountOfSteps(startNodes, input) {
  let steps = 0;
  let current = startNodes;
  while (!current.every((node) => node.isEndNode)) {
    const i = steps % input.sequence.length;
    const direction = input.sequence.charAt(i);
    const nextNodes = current.map((curr) => curr[direction]);
    current = nextNodes.map((nextNode) => input.nodes[nextNode]);
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
      nodes[element.current] = {
        L: element.L,
        R: element.R,
        isStartNode: element.isStartNode,
        isEndNode: element.isEndNode,
      };
    });
  return { sequence, nodes };
}

function parseNodeMap(line) {
  const [current, map] = line.split(" = ");
  const [L, R] = map.split(", ");
  return {
    current,
    L: L.slice(1),
    R: R.slice(0, -1),
    isStartNode: current.endsWith("A"),
    isEndNode: current.endsWith("Z"),
  };
}
