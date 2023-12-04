using AoCFramework;
using AoCFramework.Util;

namespace Day1;

public class Part2 : IAoCSolver
{
    private string example =
        @"two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen";

    private static Dictionary<string, string> lookup = new Dictionary<string, string>()
    {
        { "one", "one1one" },
        { "two", "two2two" },
        { "three", "three3three" },
        { "four", "four4four" },
        { "five", "five5five" },
        { "six", "six6six" },
        { "seven", "seven7seven" },
        { "eight", "eight8eight" },
        { "nine", "eight9eight" },
    };

    public string Solve(string input)
    {
        var p1 = new Part1();
        return p1.Solve(input.SplitOnNewline().Select(ReplaceWithDigits).ConcatBy("\r"));
    }

    private static string ReplaceWithDigits(string line)
    {
        return lookup.Aggregate(line, (acc, curr) => acc.Replace(curr.Key, curr.Value));
    }
}