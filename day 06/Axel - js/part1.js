export function part1(input) {
  const races = parseInput(input);
  return races
    .map((race) => getAmountOfWiningOptionsForRace(race))
    .reduce((acc, curr) => (acc *= curr), 1);
}

export function parseInput(input) {
  const times = parseLine(input[0]);
  const distances = parseLine(input[1]);
  return times.map((time, i) => ({ time, distance: distances[i] }));
}

export function parseLine(line) {
  const [_, numbers] = line.split(":");
  return numbers
    .split(/\s+/)
    .filter(Boolean)
    .map((num) => parseInt(num));
}

export function getAmountOfWiningOptionsForRace(race) {
  const options = [...Array(race.time + 1).keys()];
  return options.filter(
    (option) => (race.time - option) * option > race.distance
  ).length;
}
