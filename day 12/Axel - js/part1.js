export function part1(input) {
  const conditionRecords = parseInput(input);
  console.log(conditionRecords);
}

function parseInput(input) {
  return input.map((line) => parseLine(line));
}

function parseLine(line) {
  const [record, schema] = line.split(" ");
  return {
    record,
    schema: schema.split(",").map((num) => parseInt(num)),
  };
}

function getAmountOfArrangements(conditionRecord) {
}
