type FirstDigitInArray<T> = T extends [infer First, ...infer Rest]
  ? First extends `${number}`
    ? First
    : FirstDigitInArray<Rest>
  : never;

type ArrayFromString<T, R extends string[] = []> = T extends `${infer First}${infer Rest}`
  ? ArrayFromString<Rest, [...R, First]>
  : R;

type Reverse<T, R extends unknown[] = []> = T extends [infer First, ...infer Rest]
  ? Reverse<Rest, [First, ...R]>
  : R;

type FirstDigit<T> = FirstDigitInArray<ArrayFromString<T>>;
type LastDigit<T> = FirstDigitInArray<Reverse<ArrayFromString<T>>>;

type Count<T, R extends 0[] = []> = T extends R['length'] ? R : Count<T, [...R, 0]>;
type AssureNumber<T> = T extends number ? T : never;
type Inc<T> = AssureNumber<[...Count<T>, 0]['length']>;
type StringAsNumber<T, R extends number = 0> = T extends `${R}` ? R : StringAsNumber<T, Inc<R>>;

type ArraySum<A, R extends 0[] = []> = A extends [infer First, ...infer Rest]
  ? Count<First> extends 0[]
    ? ArraySum<Rest, [...R, ...Count<First>]>
    : never
  : R['length'];

// ---

type OneItemSolution<T> = StringAsNumber<`${FirstDigit<T>}${LastDigit<T>}`>;

type SolutionArr<L, R extends number[] = []> = L extends [infer First, ...infer Rest]
  ? SolutionArr<Rest, [...R, OneItemSolution<First>]>
  : R;

type Solution<T> = ArraySum<SolutionArr<T>>;

type Result = Solution<['1abc2', 'pqr3stu8vwx', 'a1b2c3d4e5f', 'treb7uchet`']>;
//   ^? type Result = 124
