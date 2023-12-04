using AoCFramework;
using AoCFramework.Util;
using System.Linq;

namespace Day1;

public class Part1 : IAoCSolver
{
    private string example =
        @"1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet";

    public string Solve(string input)
    {
        return input
            .SplitOnNewline()
            .Select(line => line.GetFirstAndLastDigit())
            .Sum(int.Parse)
            .ToString();
            ;
    }
}

internal static class Day1Extensions
{
    public static string GetFirstAndLastDigit(this string line)
    {
        var digits = line
            .Where(IsDigit).ToList();
        var first = digits.First().ToString();
        var last = digits.Last().ToString();
        return string.Join("", first, last);
    }
    
    private static bool IsDigit(this char c)
    {
        return c is >= '0' and <= '9';
    }

    private static bool IsFirstOrLastIndex(int index, string line)
    {
        return index == 0 || index == line.Length - 1;
    }
}