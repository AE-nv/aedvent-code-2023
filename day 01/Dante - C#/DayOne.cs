using System.Buffers;

namespace AdventOfCode2023.Days;

public static class DayOne
{
    private static readonly string[] DigitsAsText =
        { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    public static void Run()
    {
        var data = File.ReadAllLines("input.txt");
        var result = GetResult(data);
        Console.WriteLine(result);
    }

    internal static int GetResult(IEnumerable<string> data)
    {
        return data.Sum(x => GetSingleResult(x, fromStart: true) * 10
                             + GetSingleResult(x, fromStart: false));
    }

    private static int GetSingleResult(string entry, bool fromStart)
    {
        while (true)
        {
            var c = fromStart ? entry.First() : entry.Last();
            if (char.IsDigit(c))
            {
                return int.Parse(c.ToString());
            }

            for (var index = 0; index < DigitsAsText.Length; index++)
            {
                var wordDigit = DigitsAsText.ElementAt(index);
                var isMatch = fromStart ? entry.StartsWith(wordDigit) : entry.EndsWith(wordDigit);
                if (isMatch) return index + 1;
            }

            var remainingChars = fromStart ? entry.Skip(1) : entry.SkipLast(1);
            entry = new string(remainingChars.ToArray());
        }
    }
}
