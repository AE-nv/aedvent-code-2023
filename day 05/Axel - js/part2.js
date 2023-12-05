export function part2(input) {
  const { seeds, ...maps } = parseInput(input);
  let min = Infinity;
  seeds.forEach(({ start, end }) => {
    for (let seed = start; seed < end; seed++) {
      const location = calculateLocation(seed, maps);
      min = location < min ? location : min;
    }
  });
  return min;
}

const pipeThroughMap = (value) => ({
  value,
  to: (...args) => pipeThroughMap(calculateMapOutput(value, ...args)),
});

export function calculateLocation(seed, maps) {
  return pipeThroughMap(seed)
    .to(maps.seedToSoil)
    .to(maps.soilToFertilizer)
    .to(maps.fertilizerToWater)
    .to(maps.waterToLight)
    .to(maps.lightToTemperature)
    .to(maps.temperatureToHumidity)
    .to(maps.humidityToLocation).value;
}

export function parseSeeds(line) {
  const [_, numbers] = line.split("seeds: ");
  const parsed = numbers.split(" ").map((num) => parseInt(num));
  let result = [];
  for (let i = 0; i < parsed.length; i += 2) {
    const [start, range] = parsed.slice(i, i + 2);
    result.push({ start, end: start + range - 1 });
  }
  return result;
}

export function parseMapLine(line) {
  const [destinationStart, sourceStart, range] = line
    .split(" ")
    .map((num) => parseInt(num));
  return {
    destinationStart,
    sourceStart,
    range,
  };
}

export function parseInput(input) {
  const result = {
    seeds: [],
    seedToSoil: [],
    soilToFertilizer: [],
    fertilizerToWater: [],
    waterToLight: [],
    lightToTemperature: [],
    temperatureToHumidity: [],
    humidityToLocation: [],
  };
  let currentKey;
  input.forEach((line, lineIdx) => {
    if (lineIdx === 0) {
      result.seeds.push(...parseSeeds(line));
    } else if (line.match(/^[a-z]/)) {
      const [key, _] = line.split(" ");
      currentKey = kebabCaseToCamelCase(key);
    } else if (line.match(/^\d/)) {
      result[currentKey].push(parseMapLine(line));
    }
  });

  return result;
}

export function kebabCaseToCamelCase(input) {
  return input.replace(/-./g, (x) => x[1].toUpperCase());
}

export function calculateMapOutput(input, maps) {
  for (const map of maps) {
    const sourceEnd = map.sourceStart + map.range;
    if (input >= map.sourceStart && input <= sourceEnd) {
      const offset = input - map.sourceStart;
      return map.destinationStart + offset;
    }
  }
  return input;
}
