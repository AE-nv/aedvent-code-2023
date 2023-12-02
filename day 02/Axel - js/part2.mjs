import fs from 'fs';

function getMinimumBagForRound(round) {
  const bag = {
    red: 0,
    green: 0,
    blue: 0,
  };

  round.forEach((cubes) => {
    const [amount, color] = cubes.split(' ');
    bag[color] = parseInt(amount);
  });

  return bag;
}

function getMinimumBagForGame(game) {
  const minBags = game.map((round) => getMinimumBagForRound(round));
  const red = Math.max(...minBags.map((bag) => bag.red));
  const green = Math.max(...minBags.map((bag) => bag.green));
  const blue = Math.max(...minBags.map((bag) => bag.blue));

  return {
    red,
    green,
    blue,
  };
}

function getPowerOfBag(bag) {
  return Object.values(bag).reduce((curr, acc) => curr * acc, 1);
}

function getPowerOfGame(game) {
  const bag = getMinimumBagForGame(game);
  return getPowerOfBag(bag);
}

const file = fs.readFileSync('./input.txt');
const lines = file.toString('utf-8').split('\n');

const result = lines
  .map((line) => line.split(': ')[1])
  .map((game) => game.split('; '))
  .map((game) => game.map((round) => round.split(', ')))
  .map((game) => getPowerOfGame(game))
  .reduce((curr, acc) => (curr += acc), 0);

console.log(result);
