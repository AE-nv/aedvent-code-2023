using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var seeds = input[0].Split(": ")[1].Split(' ').Select(long.Parse).ToArray();
var maps = CreateMaps().ToArray();

var sw = Stopwatch.StartNew();
var part1 = FindLocations(seeds).Min();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

IEnumerable<Map> CreateMaps()
{
    List<MapItem> items = new();
    string source = string.Empty;
    string destination = string.Empty;
    foreach (var line in input.Skip(2))
    {
        if (string.IsNullOrEmpty(line))
        {
            yield return new Map(source, destination, items.ToArray());
            items.Clear();
            (source, destination) = (string.Empty, string.Empty);
        }
        else if (line.EndsWith("map:"))
        {
            var match = Regexes.MapRegex().Match(line);
            (source, destination) = (match.Groups["source"].Value, match.Groups["destination"].Value);
        }
        else
        {
            var numbers = line.Split(' ').Select(long.Parse).ToArray();
            var (destinationStart, sourceStart, length) = numbers.ToTuple3();
            items.Add(new(sourceStart, destinationStart, length));
        }
    }

    yield return new Map(source, destination, items.ToArray());
}

long Part2()
{
    var ranges = (
        from s in seeds.Chunked2() select (start: s.a, end: s.a + s.b - 1)).ToList();
    foreach (var map in maps)
    {
        ranges = Split(ranges, map).ToList();
    }

    return ranges.Min(r => r.start);
}

IEnumerable<(long start, long end)> Split(IEnumerable<(long start, long end)> ranges, Map map)
{
    foreach (var r in ranges)
    {
        var (start, end) = r;
        foreach (var item in map.Items.OrderBy(x => x.source))
        {
            if (start < item.Start)
            {
                yield return (start, Min(end, item.Start - 1));
                start = item.Start;
            }

            if (start <= item.End)
            {
                yield return (start + item.Offset, Min(end, item.End) + item.Offset);
                start = item.End;
            }

            if (start > end)
                break;
        }

        if (start <= end)
            yield return (start, end);
    }
}

IEnumerable<long> FindLocations(IEnumerable<long> seeds)
{
    foreach (var seed in seeds)
    {
        long value = seed;
        foreach (var map in maps)
        {
            value = map.Find(value);
        }

        yield return value;
    }
}

readonly record struct MapItem(long source, long destination, long length)
{
    public bool Contains(long value) => value >= source && value < source + length;
    public long Map(long value) => value >= source && value < source + length ? value + Offset : value;
    public long Start => source;
    public long End => source + length;
    public long Offset => destination - source;
}

class Map(string source, string destination, MapItem[] ranges)
{
    public string Source => source;
    public string Destination => destination;
    public IEnumerable<MapItem> Items => ranges;

    public long Find(long value)
    {
        foreach (var range in ranges)
        {
            if (range.Contains(value))
                return range.Map(value);
        }

        return value;
    }
}

static partial class Regexes
{
    [GeneratedRegex(@"^(?<source>[^-]*)-to-(?<destination>[^ ]*) map:$")]
    public static partial Regex MapRegex();
}