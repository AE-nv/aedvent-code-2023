export function part2(input, emptySpaceSize = 1000000) {
  const [emptyRows, emptyCols] = getExpandedGalaxy(input);
  const galaxyLocations = getGalaxyLocations(
    input,
    emptyRows,
    emptyCols,
    emptySpaceSize
  );
  const distances = getDistancesBetweenGalaxies(galaxyLocations);
  return distances.reduce((prev, curr) => prev + curr, 0);
}

export function getExpandedGalaxy(input) {
  const potentiallyEmptyCols = rangeFrom0(input[0].length);
  const emptyRows = [];
  for (let x = 0; x < input.length; x++) {
    const row = input[x];
    if (row.split("").every((char) => char === ".")) {
      emptyRows.push(x);
    }

    for (let y = 0; y < row.length; y++) {
      const element = row[y];
      if (element !== ".") {
        potentiallyEmptyCols[y] = "_";
      }
    }
  }
  const emptyCols = potentiallyEmptyCols.filter((char) => char !== "_");
  return [emptyRows, emptyCols];
}

function rangeFrom0(len) {
  return [...Array(len)].map((_, i) => i);
}

function getGalaxyLocations(input, emptyRows, emptyCols, emptySpaceSize) {
  const locations = [];
  let expandedX = 0;
  for (let x = 0; x < input.length; x++) {
    let expandedY = 0;
    const row = input[x];
    if (emptyRows.includes(x)) {
      expandedX += emptySpaceSize;
    } else {
      for (let y = 0; y < row.length; y++) {
        const element = row[y];
        if (emptyCols.includes(y)) {
          expandedY += emptySpaceSize;
        } else {
          if (element === "#") {
            locations.push([expandedX, expandedY]);
          }
          expandedY++;
        }
      }
      expandedX++;
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
