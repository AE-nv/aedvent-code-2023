using AoCFramework;
using AoCFramework.Util;
using Day2;

namespace Day3;

public class Part2 : IAoCSolver
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
        return example.SplitOnNewline()
            .Select(_ => _.Trim())
            .CreateSchematic()
            .FindNumbersAdjacentToSymbol(new SchemaSymbol("*"))
            .Sum()
            .ToString();
    }
}