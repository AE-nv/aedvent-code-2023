using System.Text.RegularExpressions;

namespace AdventOfCode2023.Days;


public static partial class DayThreePartOne
{
    private record struct Coord(int Row, int Col);
    private const char Dot = '.';

    [GeneratedRegex(@"\d+", RegexOptions.Compiled)]
    private static partial Regex NumbersRegex();

    public static void Run()
    {
        var data = File.ReadAllLines("input.txt");
        var result = GetResult(data);
        Console.WriteLine(result);
    }

    internal static int GetResult(IEnumerable<string> data)
    {
        var grid = data.Select(x => x.ToCharArray()).ToArray();
        var partNumbers = GetAllPartNumbers(grid);
        return partNumbers.Sum();
    }

    private static IEnumerable<int> GetAllPartNumbers(char[][] grid)
    {
        for (var row = 0; row < grid.Length; row++)
        {
            var line = string.Join(string.Empty, grid[row]);
            var numberMatches = NumbersRegex().Matches(line);
            foreach (Match numberMatch in numberMatches)
            {
                var start = new Coord(row, numberMatch.Index);
                var neighbors = GetNeighbors(grid, start, numberMatch.Length);
                if (neighbors.Any(IsSymbol))
                {
                    yield return int.Parse(numberMatch.Value);
                }
            }
        }
    }

    private static bool IsSymbol(char x) => !char.IsDigit(x) && !x.Equals(Dot);

    private static IEnumerable<char> GetNeighbors(char[][] grid, Coord start, int length)
    {
        var numberOfRows = grid.Length;
        var numberOfCols = grid.Select(r => r.Length).Distinct().Single();

        return GetAllNeighborCoords(start, length)
            .Where(x => x.Row >= 0 && x.Row < numberOfRows && x.Col >= 0 && x.Col < numberOfCols)
            .Select(x => grid[x.Row][x.Col]);
    }

    private static IEnumerable<Coord> GetAllNeighborCoords(Coord start, int length)
    {
        // neighbors on the same row
        yield return start with { Col = start.Col - 1 };
        yield return start with { Col = start.Col + length };

        //neighbors on row above and below
        for (var col = start.Col - 1; col <= start.Col + length; col++)
        {
            yield return new Coord(start.Row - 1, col);
            yield return new Coord(start.Row + 1, col);
        }
    }
}
