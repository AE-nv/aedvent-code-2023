using AoCFramework;
using AoCFramework.Util;

namespace Day4;

public class Part1 : IAoCSolver
{
    public const string example = @"Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";

    public string Solve(string input)
    {
        return input.SplitOnNewline()
            .Select(ScratchCard.Parse)
            .PrintDebug(_ => _.ConcatBy(","))
            .Select(_ => _.GetMatchingNumbers())
            .Select(_ => _.Count)
            .Select(_ => Math.Floor(Math.Pow(2, _ - 1)))
            .Sum()
            .ToString();
    }
}

public class ScratchCard
{
    public IList<int> OwnNumbers { get; set; }
    public IList<int> WinningNumbers { get; set; }

    public int CardNumber { get; set; }

    public static ScratchCard Parse(string line)
    {
        var scratchCard = new ScratchCard();
        var (cardPart, rest) = line.Split2(":");
        var (_, cardNumber) = cardPart.Split2(" ");
        var (left, right) = rest.Split2("|");
        scratchCard.WinningNumbers = left.Trim().SplitOnSpaces()
            .Select(int.Parse).ToList();
        scratchCard.OwnNumbers = right.Trim().SplitOnSpaces()
            .Select(int.Parse).ToList();
        scratchCard.CardNumber = int.Parse(cardNumber);
        return scratchCard;
    }

    public override string ToString()
    {
        return $"Card {CardNumber}: {WinningNumbers.ConcatBy(" ")} | {OwnNumbers.ConcatBy(" ")}";
    }

    public IList<int> GetMatchingNumbers()
    {
        return OwnNumbers.Intersect(WinningNumbers).ToList();
    }
}

static class Extensions
{
    public static (string Left, string Right) Split2(this string input, string separator)
    {
        var options = StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries;
        var split = input.Split(separator, 2, options);
        return (split[0], split[1]);
    }
}