namespace AdventOfCode;
readonly record struct Coordinate(int x, int y)
{
    public static Coordinate Origin = new(0, 0);
    public int ManhattanDistance(Coordinate o) => Abs(x - o.x) + Abs(y - o.y);
    public override string ToString() => $"({x},{y})";
    public Coordinate N => new(x, y - 1);
    public Coordinate NE => new(x + 1, y - 1);
    public Coordinate E => new(x + 1, y);
    public Coordinate SE => new(x + 1, y + 1);
    public Coordinate S => new(x, y + 1);
    public Coordinate SW => new(x - 1, y + 1);
    public Coordinate W => new(x - 1, y);
    public Coordinate NW => new(x - 1, y - 1);
    public static Coordinate operator +(Coordinate left, (int dx, int dy) p) => new(left.x + p.dx, left.y + p.dy);
}

