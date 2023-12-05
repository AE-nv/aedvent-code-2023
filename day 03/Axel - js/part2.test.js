import { expect, test, describe } from 'vitest';
import {
  findPotentialGearIndexes,
  getGearRatio,
  getSurroundingChars,
  isAdjacentToSymbol,
  isAnyCharSymbol,
  isPartOfPartNumber,
  isSymbol,
  part2,
} from './part2';

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

  const output = part2(input);

  expect(output).toBe(467835);
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

describe('find potential gear indexes', () => {
  test('1 option', () => {
    expect(findPotentialGearIndexes('012*')).toStrictEqual([3]);
  });

  test('no gears', () => {
    expect(findPotentialGearIndexes('012..#$%')).toStrictEqual([]);
  });

  test('multiple options', () => {
    expect(findPotentialGearIndexes('0*2*')).toStrictEqual([1, 3]);
  });
});

describe('isPartOfPartNumber', () => {
  test('not a number', () => {
    const line = '$1.';
    const previousLine = '...';
    const nextLine = '...';

    expect(isPartOfPartNumber(0, line, previousLine, nextLine)).toBeFalsy();
  });

  test('not adjacent to symbol', () => {
    const line = '.1.';
    const previousLine = '...';
    const nextLine = '...';

    expect(isPartOfPartNumber(1, line, previousLine, nextLine)).toBeFalsy();
  });

  test('adjacent to symbol', () => {
    const line = '.1.';
    const previousLine = '.$.';
    const nextLine = '...';

    expect(isPartOfPartNumber(1, line, previousLine, nextLine)).toBeTruthy();
  });
});

describe('getGearRatio', () => {
  test('not a gear symbol', () => {
    const previousLines = ['..35..633.', '......#...'];
    const line = '...*......';
    const nextLines = ['.....+.58.', '..592.....'];

    expect(getGearRatio(0, line, previousLines, nextLines)).toBe(0);
  });

  test('not a valid gear', () => {
    const previousLines = ['..........', '467..114..'];
    const line = '617*......';
    const nextLines = ['..35..633.', '..........'];

    expect(getGearRatio(3, line, previousLines, nextLines)).toBe(0);
  });

  test('valid gear', () => {
    const previousLines = ['..35..633.', '......#...'];
    const line = '...*......';
    const nextLines = ['.....+.58.', '..592.....'];

    expect(getGearRatio(3, line, previousLines, nextLines)).toBe(16345);
  });
});
