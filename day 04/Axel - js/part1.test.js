import { describe, expect, test } from 'vitest';
import { calculateScore, findNbMatches, parseCard, part1 } from './part1';

test('example case', () => {
  const input = [
    'Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53',
    'Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19',
    'Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1',
    'Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83',
    'Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36',
    'Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11',
  ];

  const output = part1(input);

  expect(output).toBe(13);
});

test('parse card', () => {
  const input = 'Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53';
  const result = parseCard(input);

  expect(result.winningNumbers).toStrictEqual([41, 48, 83, 86, 17]);
  expect(result.chosenNumbers).toStrictEqual([83, 86, 6, 31, 17, 9, 48, 53]);
});

describe('findNbMatches', () => {
  test('no matches', () => {
    const chosenNumbers = [1, 2, 3];
    const winningNumbers = [4, 5, 6];
    const card = {
      chosenNumbers,
      winningNumbers,
    };

    const result = findNbMatches(card);

    expect(result).toBe(0);
  });

  test('some matches', () => {
    const chosenNumbers = [1, 2, 3];
    const winningNumbers = [3, 5, 1];
    const card = {
      chosenNumbers,
      winningNumbers,
    };

    const result = findNbMatches(card);

    expect(result).toBe(2);
  });
});

test('calculate score', () => {
  expect(calculateScore(0)).toBe(0);
  expect(calculateScore(1)).toBe(1);
  expect(calculateScore(2)).toBe(2);
  expect(calculateScore(3)).toBe(4);
  expect(calculateScore(4)).toBe(8);
});
