var grid = new Grid(File.ReadAllLines("input.txt"));

var sw = Stopwatch.StartNew();
var part1 = Solve(grid, 0, 3);
var part2 = Solve(grid, 4, 10);
Console.WriteLine((part1, part2, sw.Elapsed));

int Solve(Grid grid, int min, int max)
{
    var target = grid.BottomRight;

    var queue = new PriorityQueue<Vector, int>();
    queue.Enqueue(new Vector(Coordinate.Origin, Direction.E, 0), 0);
    queue.Enqueue(new Vector(Coordinate.Origin, Direction.S, 0), 0);
    var seen = new HashSet<Vector>();

    while (queue.TryDequeue(out var vector, out var heat))
    {
        if (vector.pos == target)
        {
            return heat;
        }

        var q =
            from next in Moves(vector, min, max)
            where grid.Contains(next) && !seen.Contains(next)
            select next;

        foreach (var next in q)
        {
            seen.Add(next);
            queue.Enqueue(next, heat + grid[next.pos]);
        }
    }

    throw new Exception();
}

IEnumerable<Vector> Moves(Vector vector, int min, int max)
{
    if (vector.steps < max)
    {
        yield return vector.Move();
    }

    if (vector.steps >= min)
    {
        yield return vector.Left().Move();
        yield return vector.Right().Move();
    }
 }

readonly record struct Coordinate(int x, int y)
{
    public static Coordinate Origin = new(0, 0);
    public int ManhattanDistance(Coordinate o) => Abs(x - o.x) + Abs(y - o.y);
    public override string ToString() => $"({x},{y})";
    public static Coordinate operator +(Coordinate c, Direction d) => d switch
    {
        Direction.N => c with { y = c.y - 1 },
        Direction.E => c with { x = c.x + 1 },
        Direction.S => c with { y = c.y + 1 },
        Direction.W => c with { x = c.x - 1 },
    };
}


readonly record struct Vector(Coordinate pos, Direction d, int steps)
{
    public override string ToString() => $"{pos},{d},{steps}";
    public Vector Move() => this with { pos = pos + d, steps = steps + 1 };
    public Vector Left() => this with
    {
        d = d switch
        {
            Direction.N => Direction.W,
            Direction.W => Direction.S,
            Direction.S => Direction.E,
            Direction.E => Direction.N
        },
        steps = 0
    };
    public Vector Right() => this with
    {
        d = d switch
        {
            Direction.N => Direction.E,
            Direction.E => Direction.S,
            Direction.S => Direction.W,
            Direction.W => Direction.N
        },
        steps = 0
    };
}

class Grid
{
    readonly ImmutableDictionary<Coordinate, int> items;
    readonly Coordinate origin = new(0, 0);
    readonly Coordinate endmarker;
    public Coordinate BottomRight => new(Width - 1, Height - 1);
    public int Height => endmarker.y;
    public int Width => endmarker.x;

    public Grid(string[] input)
    {
        items = (
            from y in Range(0, input.Length) 
            from x in Range(0, input[y].Length) 
            select (x, y, c: input[y][x] - '0')
            ).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        endmarker = new(input[0].Length, input.Length);
    }

    public int this[Coordinate p] => items[p];
    public int this[(int x, int y) p] => this[new Coordinate(p.x, p.y)];
    public int this[int x, int y] => this[new Coordinate(x, y)];
    public override string ToString()
    {
        var sb = new StringBuilder();
        for (int y = origin.y; y < endmarker.y; y++)
        {
            for (int x = origin.x; x < endmarker.x; x++)
                sb.Append(this[x, y]);
            sb.AppendLine();
        }

        return sb.ToString();
    }

    public bool Contains(Coordinate c) => items.ContainsKey(c);
    public bool Contains(Vector v) => items.ContainsKey(v.pos);
}

enum Direction { N, E, S, W }