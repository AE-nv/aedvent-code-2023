export function part1(input) {
  const { seeds, ...maps } = parseInput(input);
  return seeds
    .map((seed) => calculateMapOutput(seed, maps.seedToSoil))
    .map((soil) => calculateMapOutput(soil, maps.soilToFertilizer))
    .map((fertilizer) => calculateMapOutput(fertilizer, maps.fertilizerToWater))
    .map((water) => calculateMapOutput(water, maps.waterToLight))
    .map((light) => calculateMapOutput(light, maps.lightToTemperature))
    .map((temp) => calculateMapOutput(temp, maps.temperatureToHumidity))
    .map((humidity) => calculateMapOutput(humidity, maps.humidityToLocation))
    .reduce((prev, curr) => (curr < prev ? curr : prev));
}

export function parseSeeds(line) {
  const [_, numbers] = line.split("seeds: ");
  return numbers.split(" ").map((num) => parseInt(num));
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
