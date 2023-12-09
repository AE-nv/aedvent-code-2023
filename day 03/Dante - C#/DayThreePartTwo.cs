using System.Text.RegularExpressions;

namespace AdventOfCode2023.Days;

public static partial class DayThreePartTwo
{
    private record struct Coord(int Row, int Col);

    private record struct Item(Coord Coord, char Value);

    private record struct PossibleGear(Coord Coord, int AttachedNumber);

    private const char Asterisk = '*';

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
        var ratios = GetGearRatios(grid);
        return ratios.Sum();
    }

    private static IEnumerable<int> GetGearRatios(char[][] grid)
    {
        var possibleGears = GetAllPossibleGears(grid);
        var gears = possibleGears.GroupBy(x => x.Coord).Where(g => g.Count() == 2);
        return gears.Select(g => g.Select(x => x.AttachedNumber).Aggregate(1, (x, y) => x * y));
    }

    private static IEnumerable<PossibleGear> GetAllPossibleGears(char[][] grid)
    {
        for (var row = 0; row < grid.Length; row++)
        {
            var line = string.Join(string.Empty, grid[row]);
            var numberMatches = NumbersRegex().Matches(line);
            foreach (Match numberMatch in numberMatches)
            {
                var start = new Coord(row, numberMatch.Index);
                var neighbors = GetNeighbors(grid, start, numberMatch.Length);
                foreach (var possibleGear in neighbors.Where(IsAsterisk))
                {
                    yield return new PossibleGear(possibleGear.Coord, int.Parse(numberMatch.Value));
                }
            }
        }
    }

    private static bool IsAsterisk(Item x) => x.Value.Equals(Asterisk);

    private static IEnumerable<Item> GetNeighbors(char[][] grid, Coord start, int length)
    {
        var numberOfRows = grid.Length;
        var numberOfCols = grid.Select(r => r.Length).Distinct().Single();

        return GetAllNeighborCoords(start, length)
            .Where(x => x.Row >= 0 && x.Row < numberOfRows && x.Col >= 0 && x.Col < numberOfCols)
            .Select(coord => new Item(coord, grid[coord.Row][coord.Col]));
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
