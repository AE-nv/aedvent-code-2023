var input = File.ReadAllLines("input.txt");
var grid = new FiniteGrid(input);
var serialnrs = FindSerialNrs(grid).ToArray();
var sw = Stopwatch.StartNew();

var part1 = (
    from seq in serialnrs
    let box = grid.BoundingBox(seq.position, seq.Length)
    where box.Any(c => !char.IsDigit(grid[c]) && grid[c] != '.')
    select seq.Value).Sum();

var part2 = (
    from c in grid.Points().Where(c => grid[c] == '*')
    let adjacentSerialNrs = (
        from seq in serialnrs
        let box = grid.BoundingBox(c, 1)
        where box.Intersect(seq.Coordinates()).Any()
        select seq.Value).ToArray()
    where adjacentSerialNrs.Length == 2
    select adjacentSerialNrs[0] * adjacentSerialNrs[1]).Sum();

Console.WriteLine((part1, part2, sw.Elapsed));

IEnumerable<SerialNr> FindSerialNrs(FiniteGrid grid)
{
    for (int y = 0; y < grid.Height; y++)
    {
        var x = 0;
        while (x < grid.Width)
        {
            while (!char.IsDigit(grid[x, y]) && x < grid.Width)
                x++;
            if (x >= grid.Width)
                continue;
            var position = new Coordinate(x, y);
            var sb = new StringBuilder();
            while (char.IsDigit(grid[x, y]) && x < grid.Width)
            {
                sb.Append(grid[x, y]);
                x++;
            }

            yield return new(position, sb.ToString());
        }
    }
}

record SerialNr(Coordinate position, string snr)
{
    public int Length => snr.Length;
    public long Value => long.Parse(snr);
    public IEnumerable<Coordinate> Coordinates() => Range(position.x, snr.Length).Select(x => new Coordinate(x, position.y));
}

readonly record struct Coordinate(int x, int y)
{
    public static Coordinate Origin = new(0, 0);
    public override string ToString() => $"({x},{y})";
}

class FiniteGrid
{

    //        x
    //   +---->
    //   |
    //   |
    // y v

    readonly ImmutableDictionary<Coordinate, char> items;
    readonly Coordinate origin = new(0, 0);
    readonly Coordinate bottomright;
    readonly char empty;
    public int Height => bottomright.y;
    public int Width => bottomright.x;
    public FiniteGrid(string[] input, char empty = '.')
    {
        items = (from y in Range(0, input.Length)
                 from x in Range(0, input[y].Length)
                 where input[y][x] != empty
                 select (x, y, c: input[y][x])).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        bottomright = new(input[0].Length, input.Length);
        this.empty = empty;
    }
    public char this[Coordinate p] => items.TryGetValue(p, out var c) ? c : empty;
    public char this[(int x, int y) p] => this[new Coordinate(p.x, p.y)];
    public char this[int x, int y] => this[new Coordinate(x, y)];

    public IEnumerable<Coordinate> Points() =>
        from y in Range(origin.y, bottomright.y)
        from x in Range(origin.x, bottomright.x)
        select new Coordinate(x, y);

    public IEnumerable<Coordinate> Neighbours(Coordinate p)
    {
        return
            from d in new (int x, int y)[] { (-1, 0), (0, 1), (1, 0), (0, -1) }
            where p.x + d.x >= 0
            && p.y + d.y >= 0
            && p.x + d.x < bottomright.x
            && p.y + d.y < bottomright.y
            select new Coordinate(p.x + d.x, p.y + d.y);
    }
    public IEnumerable<Coordinate> BoundingBox(Coordinate p, int length)
    {
        return
            from x in Range(p.x - 1, length + 2)
            from y in new[]{p.y - 1, p.y, p.y + 1}
            where x >= 0 && y >= 0
            && x < bottomright.x
            && y < bottomright.y
            select new Coordinate(x, y);
    }

    private IEnumerable<(Coordinate, char)> Sequence(int x, int y, Func<char,bool> predicate)
    {
        {
            yield return (new(x, y), this[x, y]);
            x++;
        }

    }

    public IEnumerable<Coordinate> InteriorPoints() =>
        from y in Range(origin.y + 1, bottomright.y - 2)
        from x in Range(origin.x + 1, bottomright.x - 2)
        select new Coordinate(x, y);

    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = origin.y; y < bottomright.y; y++)
        {
            for (int x = origin.x; x < bottomright.x; x++) sb.Append(this[x, y]);
            sb.AppendLine();
        }
        return sb.ToString();
    }
}
