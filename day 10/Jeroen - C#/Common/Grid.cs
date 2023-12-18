namespace AdventOfCode;

enum Direction{ N, NE, E, SE, S, SW, W, NW }
class Grid
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
    public Grid(string[] input, char empty = '.')
    {
        items = (from y in Range(0, input.Length)
                 from x in Range(0, input[y].Length)
                 where input[y][x] != empty
                 select (x, y, c: input[y][x])).ToImmutableDictionary(t => new Coordinate(t.x, t.y), t => t.c);
        bottomright = new(input[0].Length, input.Length);
        this.empty = empty;
    }

    private Grid(ImmutableDictionary<Coordinate,char> items, char empty, Coordinate bottomright)
    {
        this.items = items;
        this.empty = empty;
        this.bottomright = bottomright;
    }

    public Grid With(Action<ImmutableDictionary<Coordinate, char>.Builder> action) 
    {
        var builder = items.ToBuilder();
        action(builder);
        return new Grid(builder.ToImmutable(), empty, bottomright);
    }
    public Coordinate Find(char c) => items.Where(i => i.Value == c).First().Key;
    public char this[Coordinate p] => items.TryGetValue(p, out var c) ? c : empty;
    public char this[(int x, int y) p] => this[new Coordinate(p.x, p.y)];
    public char this[int x, int y] => this[new Coordinate(x, y)];

    public IEnumerable<Coordinate> Points() =>
        from y in Range(origin.y, bottomright.y)
        from x in Range(origin.x, bottomright.x)
        select new Coordinate(x, y);

    public IEnumerable<(Direction d, Coordinate c)> Neighbours(Coordinate p)
    {
        return
            from d in new (Direction direction, (int x, int y) delta)[] 
            { 
                (Direction.NW, (-1, -1)), 
                (Direction.W, (-1, 0)), 
                (Direction.SW, (-1, 1)), 
                (Direction.S, (0, 1)),
                (Direction.SE, (1, 1)), 
                (Direction.E, (1, 0)), 
                (Direction.NE, (1, -1)), 
                (Direction.N, (0, -1)) 
            }
            where IsValid(p + d.delta)
            select (d.direction, p + d.delta);
    }

    public Coordinate? GetNeighbour(Coordinate p, Direction d) => d switch
    {
        Direction.N => IfValid(new(p.x, p.y - 1)),
        Direction.NE => IfValid(new(p.x + 1, p.y - 1)),
        Direction.E => IfValid(new(p.x + 1, p.y)),
        Direction.SE => IfValid(new(p.x + 1, p.y + 1)),
        Direction.S => IfValid(new(p.x, p.y + 1)),
        Direction.SW => IfValid(new(p.x - 1, p.y + 1)),
        Direction.W => IfValid(new(p.x - 1, p.y)),
        Direction.NW => IfValid(new(p.x - 1, p.y - 1))
    };
    Coordinate? IfValid(Coordinate p) => IsValid(p) ? p : null;
    bool IsValid(Coordinate p) => p.x >= 0 && p.y >= 0 && p.x < bottomright.x && p.y < bottomright.y;

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

    public bool Contains(Coordinate c) => items.ContainsKey(c);

}

