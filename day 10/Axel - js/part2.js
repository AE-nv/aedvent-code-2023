export function part2(input) {
  const nbRows = input.length;
  const nbCols = input[0].length;

  const startTile = getStartTile(input);
  const mainLoop = constructMainLoop(input, startTile);

  let containedGround = 0;
  for (let x = 0; x < nbRows; x++) {
    for (let y = 0; y < nbCols; y++) {
      if (isGroundWithinMainLoop([x, y], input, mainLoop)) {
        containedGround++;
      }
    }
  }
  return containedGround;
}

export function isGroundWithinMainLoop(location, input, mainLoop) {
  const [x, y] = location;
  if (input[x][y] !== ".") {
    return false;
  }
  const boundingBox = getBoundingBox(mainLoop);
  if (!isWithinBoundingBox(location, boundingBox)) {
    return false;
  }

  const coords = mainLoop.map((tile) => tile.location);

  let intersections = 0;
  for (let i = 0; i < coords.length; i++) {
    const element = coords[i];
  }

  return intersections % 2 === 0;
}

function getBoundingBox(mainLoop) {
  const loopLocations = mainLoop.map((tile) => tile.location);
  let xMin,
    yMin = Infinity;
  let xMax,
    yMax = 0;
  for (const loopLocation of loopLocations) {
    const [x, y] = loopLocation;
    xMin = Math.min(xMin, x);
    xMax = Math.max(xMax, x);
    yMin = Math.min(yMin, y);
    yMax = Math.max(yMax, y);
  }
  return [xMin, xMax, yMin, yMax];
}

function isWithinBoundingBox(location, boundingBox) {
  const [xMin, xMax, yMin, yMax] = boundingBox;
  const [x, y] = location;
  return !(x < xMin || x > xMax || y < yMin || y > yMax);
}

function constructMainLoop(input, startTile) {
  let currentTile = getNextTile(input, startTile);
  const mainLoop = [currentTile];
  while (!nextTileIsStart(currentTile, startTile)) {
    currentTile = getNextTile(input, currentTile);
    mainLoop.push(currentTile);
  }
  return mainLoop;
}

function nextTileIsStart(currentTile, startTile) {
  return (
    currentTile.nextLocation[0] === startTile.location[0] &&
    currentTile.nextLocation[1] === startTile.location[1]
  );
}

function getStartTile(input) {
  for (let i = 0; i < input.length; i++) {
    const line = input[i];
    const startTileIndex = line.indexOf("S");
    if (startTileIndex !== -1) {
      const location = [i, startTileIndex];
      const [previousLocation, nextLocation] = getSurroundingLocations(
        input,
        location
      );
      return {
        location,
        previousLocation,
        nextLocation,
      };
    }
  }
}

function getSurroundingLocations(input, startTileLocation) {
  const [row, col] = startTileLocation;
  const surroundingLocations = [];

  const charAboveStart = input[row - 1]?.[col];
  if (["7", "F", "|"].includes(charAboveStart)) {
    surroundingLocations.push([row - 1, col]);
  }
  const charBelowStart = input[row + 1]?.[col];
  if (["J", "L", "|"].includes(charBelowStart)) {
    surroundingLocations.push([row + 1, col]);
  }
  const charLeftOfStart = input[row]?.[col - 1];
  if (["F", "L", "-"].includes(charLeftOfStart)) {
    surroundingLocations.push([row, col - 1]);
  }
  const charRightOfStart = input[row]?.[col + 1];
  if (["7", "J", "-"].includes(charRightOfStart)) {
    surroundingLocations.push([row, col + 1]);
  }

  return surroundingLocations;
}

function getNextTile(input, currentTile) {
  const location = currentTile.nextLocation;
  const direction = getPreviousDirection(currentTile.location, location);

  const [row, col] = location;
  const char = input[row][col];

  const nextLocation = getNextLocation(location, char, direction);

  return {
    location,
    previousLocation: currentTile.location,
    nextLocation,
  };
}

function getPreviousDirection(currentLocation, nextLocation) {
  const [currRow, currCol] = currentLocation;
  const [nextRow, nextCol] = nextLocation;

  if (nextRow > currRow) return "down";
  if (nextRow < currRow) return "up";
  if (nextCol > currCol) return "right";
  if (nextCol < currCol) return "left";
}

function getNextLocation(currentPosition, currentChar, previousDirection) {
  switch (previousDirection) {
    case "down":
      return getNextFromDown(currentPosition, currentChar);
    case "up":
      return getNextFromUp(currentPosition, currentChar);
    case "left":
      return getNextFromLeft(currentPosition, currentChar);
    case "right":
      return getNextFromRight(currentPosition, currentChar);
  }
}

function getNextFromDown(currentPosition, currentChar) {
  const [row, col] = currentPosition;
  switch (currentChar) {
    case "J":
      return [row, col - 1];
    case "L":
      return [row, col + 1];
    case "|":
      return [row + 1, col];
  }
}

function getNextFromUp(currentPosition, currentChar) {
  const [row, col] = currentPosition;
  switch (currentChar) {
    case "7":
      return [row, col - 1];
    case "F":
      return [row, col + 1];
    case "|":
      return [row - 1, col];
  }
}

function getNextFromLeft(currentPosition, currentChar) {
  const [row, col] = currentPosition;
  switch (currentChar) {
    case "F":
      return [row + 1, col];
    case "L":
      return [row - 1, col];
    case "-":
      return [row, col - 1];
  }
}

function getNextFromRight(currentPosition, currentChar) {
  const [row, col] = currentPosition;
  switch (currentChar) {
    case "7":
      return [row + 1, col];
    case "J":
      return [row - 1, col];
    case "-":
      return [row, col + 1];
  }
}
