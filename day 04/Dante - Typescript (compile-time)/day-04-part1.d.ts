type Count<T, R extends 0[] = []> = T extends R['length'] ? R : Count<T, [...R, 0]>;
type Inc<T> = [...Count<T>, 0]['length'];
type Dec<T> = Count<T> extends [0, ...infer Rest] ? Rest['length'] : 0;

type ArraySum<A, R extends 0[] = []> = A extends [infer First, ...infer Rest]
  ? Count<First> extends 0[]
    ? ArraySum<Rest, [...R, ...Count<First>]>
    : never
  : R['length'];

type Split<T, Sep extends string> = T extends `${infer First}${Sep}${infer Rest}`
  ? [First, ...Split<Rest, Sep>]
  : [T];

// ---

type CorrectGuessesCount<WinningNumbers extends any[], Numbers extends any[], R = 0> = Numbers extends [
  infer First,
  ...infer Rest
]
  ? First extends ''
    ? CorrectGuessesCount<WinningNumbers, Rest, R>
    : First extends WinningNumbers[number]
    ? CorrectGuessesCount<WinningNumbers, Rest, Inc<R>>
    : CorrectGuessesCount<WinningNumbers, Rest, R>
  : R;

type Score<T, R extends 0[] = []> = T extends 0
  ? R['length']
  : R['length'] extends 0
  ? Score<Dec<T>, [0]>
  : Score<Dec<T>, [...R, ...R]>;

type LineScore<Line> = Line extends `Card ${number}: ${infer WinningNumbers} | ${infer Numbers}`
  ? Score<CorrectGuessesCount<Split<WinningNumbers, ' '>, Split<Numbers, ' '>>>
  : 0;

type SolutionArr<L, R extends number[] = []> = L extends [infer First, ...infer Rest]
  ? SolutionArr<Rest, [...R, LineScore<First>]>
  : R;

type Solution<T> = ArraySum<SolutionArr<T>>;

type Result = Solution<
  //   ^? type Result = 13
  [
    'Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53',
    'Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19',
    'Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1',
    'Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83',
    'Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36',
    'Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11'
  ]
>;

export {}; // Prevent global namespace pollution
