namespace AdventOfCode
{
    static partial class TupleUtils
    {
        public static (T a, T b) Sort<T>(this (T a, T b) range) where T : INumber<T> => range.a > range.b ? (range.b, range.a) : range;
        public static bool Contains<T>(this (T a, T b) range, T n) where T : INumber<T> 
        {
            var (a, b) = range.Sort();
            return a <= n && b >= n;
        }
    }
}
