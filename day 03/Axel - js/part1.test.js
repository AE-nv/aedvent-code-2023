import { expect, test, describe } from 'vitest';
import {
  getSurroundingChars,
  isAdjacentToSymbol,
  isAnyCharSymbol,
  isSymbol,
  part1,
} from './part1';

test('example case', () => {
  const input = [
    '467..114..',
    '...*......',
    '..35..633.',
    '......#...',
    '617*......',
    '.....+.58.',
    '..592.....',
    '......755.',
    '...$.*....',
    '.664.598..',
  ];

  const output = part1(input);

  expect(output).toBe(4361);
});

test('isAnyCharSymbol', () => {
  expect(isAnyCharSymbol(['.', '1', '2'])).toBeFalsy();
  expect(isAnyCharSymbol(['.', 'f', '2'])).toBeTruthy();
});

describe('isSymbol', () => {
  test('empty char', () => {
    expect(isSymbol('.')).toBeFalsy();
  });

  test('number', () => {
    expect(isSymbol('1')).toBeFalsy();
  });

  test('symbol', () => {
    expect(isSymbol('$')).toBeTruthy();
  });
});

describe('GetSurroundingChars', () => {
  test('middle of line', () => {
    const result = getSurroundingChars(2, 'abcdef');
    expect(result).toStrictEqual(['b', 'c', 'd']);
  });

  test('start of line', () => {
    const result = getSurroundingChars(0, 'abcdef');
    expect(result).toStrictEqual(['.', 'a', 'b']);
  });

  test('end of line', () => {
    const result = getSurroundingChars(5, 'abcdef');
    expect(result).toStrictEqual(['e', 'f', '.']);
  });
});

describe('isAdjacentToSymbol', () => {
  test('previous char is symbol', () => {
    const line = '$1.';
    const previousLine = '...';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('next char is symbol', () => {
    const line = '.1$';
    const previousLine = '...';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('previous line has diagonal symbol to left', () => {
    const line = '.1.';
    const previousLine = '$..';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('previous line has diagonal symbol to right', () => {
    const line = '.1.';
    const previousLine = '..#';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('previous line has symbol at index', () => {
    const line = '.1.';
    const previousLine = '.$.';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('next line has diagonal symbol to left', () => {
    const line = '.1.';
    const previousLine = '...';
    const nextLine = '$..';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('next line has diagonal symbol to right', () => {
    const line = '.1.';
    const previousLine = '...';
    const nextLine = '^..';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('next line has symbol at index', () => {
    const line = '.1.';
    const previousLine = '...';
    const nextLine = '.$.';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeTruthy();
  });

  test('No adjacent symbols', () => {
    const line = '.1.';
    const previousLine = '...';
    const nextLine = '...';

    expect(isAdjacentToSymbol(1, line, previousLine, nextLine)).toBeFalsy();
  });
});
