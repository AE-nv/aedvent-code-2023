using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var grid = new Grid(input);
var sw = Stopwatch.StartNew();
var part1 = Solve(2);
var part2 = Solve(1000000);
Console.WriteLine((part1, part2, sw.Elapsed));

long Solve(int n)
{
    var emptyrows = (
        from r in grid.Rows
        where r.row.All(c => c == '.')
        select r.y).Reverse().ToArray();
    var emptycols = (
        from c in grid.Columns
        where c.column.All(c => c == '.')
        select c.x).Reverse().ToArray();
    var points = (
        from item in grid.Points()
        where grid[item] == '#'
        select item).ToList();
    var distances =
        from p1 in points
        from p2 in points
        let ranges = (x: (p1.x, p2.x), y: (p1.y, p2.y))
        let emptyr = emptyrows.Count(y => ranges.y.Contains(y))
        let emptyc = emptycols.Count(x => ranges.x.Contains(x))
        select p1.ManhattanDistance(p2) + emptyr * (n - 1L) + emptyc * (n - 1L);
    return distances.Sum() / 2;
}

readonly record struct Coordinate(int x, int y)
{
    public static Coordinate Origin = new(0, 0);
    public int ManhattanDistance(Coordinate o) => Abs(x - o.x) + Abs(y - o.y);
    public override string ToString() => $"({x},{y})";
    public static Coordinate operator +(Coordinate left, (int dx, int dy) p) => new(left.x + p.dx, left.y + p.dy);
}

class Grid
{
    readonly ImmutableDictionary<Coordinate, char> items;
    readonly Coordinate origin = new(0, 0);
    readonly Coordinate bottomright;
    readonly char empty;
    public Grid(string[] input, char empty = '.')
    {
        items = (
            from y in Range(0, input.Length)
            from x in Range(0, input[y].Length)
            where input[y][x] != empty
            select (x, y, c: input[y][x])).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        bottomright = new(input[0].Length, input.Length);
        this.empty = empty;
    }

    public IEnumerable<(int x, IEnumerable<char> column)> Columns
    {
        get
        {
            for (int x = 0; x < bottomright.x; x++)
            {
                yield return (x, from y in Range(0, bottomright.y) select this[x, y]);
            }
        }
    }

    public IEnumerable<(int y, IEnumerable<char> row)> Rows
    {
        get
        {
            for (int y = 0; y < bottomright.y; y++)
            {
                yield return (y, from x in Range(0, bottomright.x) select this[x, y]);
            }
        }
    }

    public char this[Coordinate p] => items.TryGetValue(p, out var c) ? c : empty;
    public char this[(int x, int y) p] => this[new Coordinate(p.x, p.y)];
    public char this[int x, int y] => this[new Coordinate(x, y)];
    public IEnumerable<Coordinate> Points() =>
        from y in Range(origin.y, bottomright.y) from x in Range(origin.x, bottomright.x) select new Coordinate(x, y);
  
    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = origin.y; y < bottomright.y; y++)
        {
            for (int x = origin.x; x < bottomright.x; x++)
                sb.Append(this[x, y]);
            sb.AppendLine();
        }

        return sb.ToString();
    }

}