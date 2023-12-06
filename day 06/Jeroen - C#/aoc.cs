var input = File.ReadAllLines("input.txt");

var times = input[0].Split(':', StringSplitOptions.TrimEntries)[1].Split(' ', StringSplitOptions.RemoveEmptyEntries).Select(int.Parse);
var records = input[1].Split(':', StringSplitOptions.TrimEntries)[1].Split(' ', StringSplitOptions.RemoveEmptyEntries).Select(int.Parse);

var time = long.Parse(new(input[0].Where(char.IsDigit).ToArray()));
var record = long.Parse(new(input[1].Where(char.IsDigit).ToArray()));

var sw = Stopwatch.StartNew();
var part1 = (
    from z in times.Zip(records)
    let t = z.First
    let r = z.Second
    let n = (
        from x in Range(1, z.First - 1)
        let d = (t - x) * x
        where d > r
        select x).Count()
    select n).Aggregate(1, (a, b) => a * b);
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
long Part2()
{
    var lower = Range(1L, time - 1).First(speed => (time - speed) * speed > record);
    var upper = Range(time, 1, -1).First(speed => (time - speed) * speed > record);
    return upper - lower + 1;
}

IEnumerable<long> Range(long start, long length, int step = 1)
{
    for (var i = start; i <= start + length; i += step)
    {
        yield return i;
    }
}