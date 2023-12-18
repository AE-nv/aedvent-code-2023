using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var items1 = input.Select(s => Regexes.RegexPart1().As<Item>(s)).ToImmutableArray();
var items2 = input.Select(s =>
    {
        var match = Regexes.RegexPart2().Match(s);
        var distance = Convert.ToInt32(match.Groups["distance"].Value, 16);
        var dir = match.Groups["dir"].Value[0] switch { '0' => 'R', '1' => 'D', '2' => 'L', '3' => 'U' };
        return new Item(dir, distance);
    }) .ToImmutableArray();

var sw = Stopwatch.StartNew();
var part1 = Solve(items1);
var part2 = Solve(items2);
Console.WriteLine((part1, part2, sw.Elapsed));

long Solve(IEnumerable<Item> items)
{
    var q =
        from item in items
        select (item.distance, dx: item.direction switch
        {
            'R' => item.distance,
            'L' => -item.distance,
            _ => 0
        }, dy: item.direction switch
        {
            'D' => item.distance,
            'U' => -item.distance,
            _ => 0
        });

    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    // https://en.wikipedia.org/wiki/Shoelace_formula
    var current = (x: 0L, y: 0L);
    var (area, perimeter) = (0L, 0L);
    foreach (var (distance, dx, dy) in q)
    {
        var next = (x: current.x + dx, y: current.y + dy);
        area += (current.x * next.y) - (current.y * next.x);
        perimeter += distance;
        current = next;
    }

    area = (area + current.x - current.y) / 2;
    area = area + (perimeter / 2) + 1;
    return area;
}

readonly record struct Item(char direction, int distance);

static partial class Regexes
{
    [GeneratedRegex(@"^(?<direction>[RLUD]) (?<distance>\d+) .*$")]
    public static partial Regex RegexPart1();
    [GeneratedRegex(@"^[RLUD] \d+ \(#(?<distance>\w{5})(?<dir>\w)\)$")]
    public static partial Regex RegexPart2();
}