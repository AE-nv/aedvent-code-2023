using System.Text.RegularExpressions;

var data = File.ReadAllLines("input.txt");
var result = GetResult(data);
Console.WriteLine(result);

record struct Coord(int Row, int Col);
const char Dot = '.';

static int GetResult(IEnumerable<string> data)
{
    var grid = data.Select(x => x.ToCharArray()).ToArray();
    var partNumbers = GetAllPartNumbers(grid);
    return partNumbers.Sum();
}

static IEnumerable<int> GetAllPartNumbers(char[][] grid)
{
    for (var row = 0; row < grid.Length; row++)
    {
        var line = string.Join(string.Empty, grid[row]);
        var numberMatches = new Regex(@"\d+").Matches(line);
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

static bool IsSymbol(char x) => !char.IsDigit(x) && !x.Equals(Dot);

static IEnumerable<char> GetNeighbors(char[][] grid, Coord start, int length)
{
    var numberOfRows = grid.Length;
    var numberOfCols = grid.Select(r => r.Length).Distinct().Single();

    return GetAllNeighborCoords(start, length)
        .Where(x => x.Row >= 0 && x.Row < numberOfRows && x.Col >= 0 && x.Col < numberOfCols)
        .Select(x => grid[x.Row][x.Col]);
}

static IEnumerable<Coord> GetAllNeighborCoords(Coord start, int length)
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
