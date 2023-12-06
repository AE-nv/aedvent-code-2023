export function part2(input) {
  const race = parseInput(input);
  return getAmountOfWiningOptionsForRace(race);
}

export function parseInput(input) {
  const time = parseLine(input[0]);
  const distance = parseLine(input[1]);
  return { time, distance };
}

export function parseLine(line) {
  const [_, numbers] = line.split(":");
  const splitNumbers = numbers.split(/\s+/).filter(Boolean);
  return parseInt(splitNumbers.join(""));
}

export function getAmountOfWiningOptionsForRace(race) {
  const options = [...Array(race.time + 1).keys()];
  return options.filter(
    (option) => (race.time - option) * option > race.distance
  ).length;
}
