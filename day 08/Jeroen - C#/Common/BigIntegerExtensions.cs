namespace AdventOfCode;

internal static class BigIntegerExtensions
{
    public static BigInteger LeastCommonMultiplier(BigInteger left, BigInteger right)
    {
        return left * right / BigInteger.GreatestCommonDivisor(left, right);
    }
    public static BigInteger LeastCommonMultiplier(BigInteger left, params BigInteger[] right)
    {
        BigInteger lcm = left;
        foreach (var item in right)
        {
            lcm = LeastCommonMultiplier(lcm, item);
        }
        return lcm;
    }

    public static BigInteger LeastCommonMultiplier(this IEnumerable<BigInteger> numbers)
    {
        BigInteger lcm = 1;
        foreach (var item in numbers)
        {
            lcm = LeastCommonMultiplier(lcm, item);
        }
        return lcm;
    }
    public static BigInteger LeastCommonMultiplier(this IEnumerable<long> numbers)
    {
        BigInteger lcm = 1;
        foreach (var item in numbers)
        {
            lcm = LeastCommonMultiplier(lcm, item);
        }
        return lcm;
    }
    public static BigInteger LeastCommonMultiplier(this IEnumerable<int> numbers)
    {
        BigInteger lcm = 1;
        foreach (var item in numbers)
        {
            lcm = LeastCommonMultiplier(lcm, item);
        }
        return lcm;
    }
}
