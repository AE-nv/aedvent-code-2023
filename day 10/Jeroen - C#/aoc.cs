using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var (start, grid, loop) = Initialize(input);

var sw = Stopwatch.StartNew();
var part1 = loop.Count % 2 == 0 ? loop.Count / 2 : loop.Count / 2 - 1;
var part2 = (
    from p in grid.Points()
    where grid[p] == '.'
    let count = CountLinesCrossed(grid, p, loop)
    where count % 2 == 1
    select p).Count();
Console.WriteLine((part1, part2, sw.Elapsed));

(Coordinate start, Grid grid, HashSet<Coordinate> loop) Initialize(string[] input)
{
    Grid grid = new(input);
    var start = grid.Find('S');
    var connection = DetermineConnection(grid, start);
    grid = grid.With(b => b[start] = connection);
    var loop = FindPath(grid, start).ToHashSet();
    grid = grid.With(b =>
    {
        b[start] = connection;
        var notinloop = b.Keys.Where(c => !loop.Contains(c)).ToList();
        foreach (var item in notinloop)
        {
            b[item] = '.';
        }
    });
    return (start, grid, loop);
}

IEnumerable<Coordinate> FindPath(Grid grid, Coordinate start)
{
    var current = start;
    var next = ConnectedNeighbours(grid, current).First();
    Coordinate? previous = null;
    do
    {
        yield return current;
        next = ConnectedNeighbours(grid, current).First(c => c != previous);
        previous = current;
        current = next;
    }
    while (current != start);
}
IEnumerable<Coordinate> ConnectedNeighbours(Grid grid, Coordinate c) => grid[c] switch
{
    '|' => [c.N, c.S],
    '-' => [c.E, c.W],
    'L' => [c.N, c.E],
    'J' => [c.N, c.W],
    'F' => [c.S, c.E],
    '7' => [c.S, c.W],
    _ => Array.Empty<Coordinate>()
};

char DetermineConnection(Grid grid, Coordinate start) => (
        from x in grid.Neighbours(start)
        where (x.d, grid[x.c]) switch
        {
            (Direction.N, '|' or 'F' or '7') => true,
            (Direction.E, '-' or 'J' or '7') => true,
            (Direction.S, '|' or 'J' or 'L') => true,
            (Direction.W, '-' or 'F' or 'L') => true,
            _ => false
        }
        orderby x.d
        select x.d).ToTuple2() switch
        {
            (Direction.N, Direction.E) => 'L',
            (Direction.N, Direction.S) => '|',
            (Direction.N, Direction.W) => 'J',
            (Direction.E, Direction.S) => 'F',
            (Direction.E, Direction.W) => '-',
            (Direction.S, Direction.W) => '7',
        };

int CountLinesCrossed(Grid grid, Coordinate p, HashSet<Coordinate> loop)
{
    Func<char, Result> f = OutsideLoop;
    int count = 0;
    var e = East(grid, p).GetEnumerator();
    while (e.MoveNext())
    {
        (f, var cross) = f(grid[e.Current]);
        if (cross) count++;
    }
    return count;
}

IEnumerable<Coordinate> East(Grid grid, Coordinate c)
{
    var current = c;
    while (current.x < grid.Width)
    {
        yield return current;
        current = current.E;
    }
}

Result OutsideLoop(char c) => c switch
{
    'F' => new(OutsideOnF, false),
    'L' => new(OutsideOnL, false),
    '|' => new(InsideLoop, true),
    '.' => new(OutsideLoop, false)
};
Result OutsideOnF(char c) => c switch
{
    '-' => new(OutsideOnF, false),
    'J' => new(InsideLoop, true),
    '7' => new(OutsideLoop, false),
};
Result OutsideOnL(char c) => c switch
{
    '-' => new(OutsideOnL, false),
    'J' => new(OutsideLoop, false),
    '7' => new(InsideLoop, true)
};
Result InsideOnF(char c) => c switch
{
    '-' => new(InsideOnF, false),
    'J' => new(OutsideLoop, true),
    '7' => new(InsideLoop, false),
};
Result InsideOnL(char c) => c switch
{
    '-' => new(InsideOnL, false),
    'J' => new(InsideLoop, false),
    '7' => new(OutsideLoop, true)
};
Result InsideLoop(char c) => c switch
{
    'F' => new(InsideOnF, false),
    'L' => new(InsideOnL, false),
    '|' => new(OutsideLoop, true),
    '.' => new(InsideLoop, false)
};
record struct Result(Func<char, Result> Next, bool cross);
