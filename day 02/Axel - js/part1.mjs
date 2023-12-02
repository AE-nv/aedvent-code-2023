import fs from 'fs';

const bag = {
  red: 12,
  green: 13,
  blue: 14,
};

function isValidRound(round) {
  for (const cubes of round) {
    const [amount, color] = cubes.split(' ');
    if (bag[color] < parseInt(amount)) {
      return false;
    }
  }
  return true;
}

function isValidGame(game) {
  return game.every((round) => isValidRound(round));
}

function sumValidGameNumbers(games) {
  let result = 0;

  for (const [index, game] of games.entries()) {
    if (isValidGame(game)) {
      result += index + 1;
    }
  }
  return result;
}

const file = fs.readFileSync('./input.txt');
const lines = file.toString('utf-8').split('\n');

const games = lines
  .map((line) => line.split(': ')[1])
  .map((game) => game.split('; '))
  .map((game) => game.map((round) => round.split(', ')));

const result = sumValidGameNumbers(games);
console.log(result);
