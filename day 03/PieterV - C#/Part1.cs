using AoCFramework;
using AoCFramework.Util;
using Day2;

namespace Day3;

public class Part1 : IAoCSolver
{
    private const string example = @"467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..";

    public string Solve(string input)
    {
        return input.SplitOnNewline()
            .Select(_ => _.Trim())
            .CreateSchematic()
            .FindNumbersAdjacentToSymbols()
            .Sum()
            .ToString()!;
    }
}

static class Extensions
{
    public static EngineSchematic CreateSchematic(this IEnumerable<string> input)
    {
        var engineSchematic = new EngineSchematic();
        return input.Aggregate(engineSchematic, (acc, row) => acc.AddSchemaRow(row));
    }
}