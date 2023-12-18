using AdventOfCode;
var grid = new Grid(File.ReadAllLines("input.txt"));

var sw = Stopwatch.StartNew();
Console.WriteLine("started");
var part1 = Calculate(new(new(-1, 0), Direction.E));
Console.WriteLine("part1 done");
var part2 = (
        from x in Range(0, grid.Width) 
        from p in ((y: -1, d: Direction.S), (y: grid.Height, d: Direction.N)).AsEnumerable() 
        select new Vector(new(x, p.y), p.d)
    ).Concat(
        from y in Range(0, grid.Height) 
        from p in ((x: -1, d: Direction.E), (x: grid.Width, d: Direction.W)).AsEnumerable() 
        select new Vector(new(p.x, y), p.d)
    ).Select(Calculate).Max();

Console.WriteLine((part1, part2, sw.Elapsed));

int Calculate(Vector v) => FindEnergizedSpots(grid, v, []).Select(v => v.pos).Distinct().Count() - 1;

IEnumerable<Vector> FindEnergizedSpots(Grid grid, Vector v, HashSet<Vector> seen)
{
    if (seen.Contains(v))
        yield break;
    yield return v;
    seen.Add(v);
    foreach (var w in v.Advance(grid))
    {
        foreach (var item in FindEnergizedSpots(grid, w, seen))
        {
            yield return item;
        }
    }
}

readonly record struct Coordinate(int x, int y)
{
    public static Coordinate Origin = new(0, 0);
    public override string ToString() => $"({x},{y})";
    public Coordinate N => new(x, y - 1);
    public Coordinate E => new(x + 1, y);
    public Coordinate S => new(x, y + 1);
    public Coordinate W => new(x - 1, y);
}

readonly record struct Vector(Coordinate pos, Direction d)
{
    public override string ToString() => $"{pos},{d}";
    internal IEnumerable<Vector> Advance(Grid grid)
    {
        var go = grid.Go(pos, d);
        if (go is null)
            return Empty<Vector>();
        var next = go.Value;
        return (grid[next], d) switch
        {
            ('.', _) or ('-', Direction.E or Direction.W) or ('|', Direction.N or Direction.S) => new Vector(next, d).AsEnumerable(),
            ('-', Direction.N or Direction.S) or ('|', Direction.E or Direction.W) => Split(d).Select(x => new Vector(next, x)),
            ('/', Direction.N) => new Vector(next, Direction.E).AsEnumerable(),
            ('/', Direction.E) => new Vector(next, Direction.N).AsEnumerable(),
            ('/', Direction.S) => new Vector(next, Direction.W).AsEnumerable(),
            ('/', Direction.W) => new Vector(next, Direction.S).AsEnumerable(),
            ('\\', Direction.N) => new Vector(next, Direction.W).AsEnumerable(),
            ('\\', Direction.W) => new Vector(next, Direction.N).AsEnumerable(),
            ('\\', Direction.E) => new Vector(next, Direction.S).AsEnumerable(),
            ('\\', Direction.S) => new Vector(next, Direction.E).AsEnumerable()
        };
    }

    internal static IEnumerable<Direction> Split(Direction d) => d switch
    {
        Direction.N or Direction.S => [Direction.E, Direction.W],
        Direction.E or Direction.W => [Direction.N, Direction.S]
    };
}

class Grid
{
    readonly ImmutableDictionary<Coordinate, char> items;
    readonly Coordinate origin = new(0, 0);
    readonly Coordinate bottomright;
    readonly char empty;
    public int Height => bottomright.y;
    public int Width => bottomright.x;

    public Grid(string[] input, char empty = '.')
    {
        items = (
            from y in Range(0, input.Length)
            from x in Range(0, input[y].Length)
            where input[y][x] != empty
            select (x, y, c: input[y][x])
        ).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        bottomright = new(input[0].Length, input.Length);
        this.empty = empty;
    }

    private Grid(ImmutableDictionary<Coordinate, char> items, char empty, Coordinate bottomright)
    {
        this.items = items;
        this.empty = empty;
        this.bottomright = bottomright;
    }

    public char this[Coordinate p] => items.TryGetValue(p, out var c) ? c : empty;
    public char this[int x, int y] => this[new Coordinate(x, y)];
    Coordinate? IfValid(Coordinate p) => IsValid(p) ? p : null;
    bool IsValid(Coordinate p) => p.x >= 0 && p.y >= 0 && p.x < bottomright.x && p.y < bottomright.y;
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

    public Coordinate? Go(Coordinate c, Direction d) => d switch
    {
        Direction.N => IfValid(c.N),
        Direction.E => IfValid(c.E),
        Direction.S => IfValid(c.S),
        Direction.W => IfValid(c.W)
    };
}

public enum Direction
{
    N,
    E,
    S,
    W
}