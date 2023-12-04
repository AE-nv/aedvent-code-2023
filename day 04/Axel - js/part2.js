import fs from 'fs';

function readInput() {
  const file = fs.readFileSync('./input.txt');
  return file.toString('utf8').split('\n');
}

export function part2(input) {
  const cards = input.map((line) => parseCard(line));

  cards.forEach((card, cardIndex) => {
    const nbMatches = findNbMatches(card);
    for (let i = 1; i < nbMatches + 1; i++) {
      if (i >= cards.length - cardIndex) {
        break;
      }
      cards[i + cardIndex].amount += card.amount;
    }
  });

  return cards
    .map((card) => card.amount)
    .reduce((curr, acc) => (curr += acc), 0);
}

export function parseCard(line) {
  const [card, numbers] = line.split(': ');
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
    card: parseInt(/\d+/.exec(card)),
    winningNumbers,
    chosenNumbers,
    amount: 1,
  };
}

export function findNbMatches(card) {
  return card.chosenNumbers.filter((number) =>
    card.winningNumbers.includes(number)
  ).length;
}

const input = readInput();
const result = part2(input);
console.log(result);
