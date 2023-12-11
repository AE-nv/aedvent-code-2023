export function part1(input) {
  const expandedGalaxy = getExpandedGalaxy(input);
  const galaxyLocations = getGalaxyLocations(expandedGalaxy);
  const distances = getDistancesBetweenGalaxies(galaxyLocations);
  return distances.reduce((prev, curr) => prev + curr, 0);
}

export function getExpandedGalaxy(input) {
  const rowResult = [];

  const potentiallyEmptyCols = rangeFrom0(input[0].length);

  for (let x = 0; x < input.length; x++) {
    const row = input[x];
    rowResult.push(row);
    if (row.split("").every((char) => char === ".")) {
      rowResult.push(row);
    }

    for (let y = 0; y < row.length; y++) {
      const element = row[y];
      if (element !== ".") {
        potentiallyEmptyCols[y] = "_";
      }
    }
  }
  const emptyCols = potentiallyEmptyCols.filter((char) => char !== "_");

  const result = [];
  for (let x = 0; x < rowResult.length; x++) {
    const expandedRow = [];
    const row = rowResult[x];
    row.split("").forEach((char, y) => {
      expandedRow.push(char);
      if (emptyCols.includes(y)) {
        expandedRow.push(char);
      }
    });
    result.push(expandedRow.join(""));
  }

  return result;
}

function rangeFrom0(len) {
  return [...Array(len)].map((_, i) => i);
}

function getGalaxyLocations(expandedGalaxy) {
  const locations = [];
  for (let x = 0; x < expandedGalaxy.length; x++) {
    const row = expandedGalaxy[x];
    for (let y = 0; y < row.length; y++) {
      const element = row[y];
      if (element === "#") {
        locations.push([x, y]);
      }
    }
  }
  return locations;
}

function getDistancesBetweenGalaxies(galaxies) {
  const pairs = getAllPairs(galaxies);
  return pairs.map((pair) => getManhattanDistance(pair[0], pair[1]));
}

function getAllPairs(galaxies) {
  return galaxies
    .map((galaxy, i) =>
      galaxies.slice(i + 1).map((otherGalaxy) => [galaxy, otherGalaxy])
    )
    .flat();
}

function getManhattanDistance(galaxy1, galaxy2) {
  const [x1, y1] = galaxy1;
  const [x2, y2] = galaxy2;
  return Math.abs(x1 - x2) + Math.abs(y1 - y2);
}
