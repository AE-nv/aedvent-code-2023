import fs from 'fs';

function readInput() {
  const file = fs.readFileSync('./input.txt');
  return file.toString('utf8').split('\n');
}

export function part1(input) {
  return input
    .map((line) => parseCard(line))
    .map((card) => findNbMatches(card))
    .map((nbMatches) => calculateScore(nbMatches))
    .reduce((curr, acc) => (curr += acc), 0);
}

export function parseCard(line) {
  const [_, numbers] = line.split(': ');
  const [winningString, chosenString] = numbers.split(' | ');
  const winningNumbers = winningString
    .split(' ')
    .map((str) => str.replace(/\s/g, ''))
    .filter(Boolean)
    .map((num) => parseInt(num));
  const chosenNumbers = chosenString
    .split(' ')
    .map((str) => str.replace(/\s/g, ''))
    .filter(Boolean)
    .map((num) => parseInt(num));

  return {
    winningNumbers,
    chosenNumbers,
  };
}

export function findNbMatches(card) {
  return card.chosenNumbers.filter((number) =>
    card.winningNumbers.includes(number)
  ).length;
}

export function calculateScore(nbMatches) {
  return nbMatches > 0 ? Math.pow(2, nbMatches - 1) : 0;
}

const input = readInput();
const result = part1(input);
console.log(result);
