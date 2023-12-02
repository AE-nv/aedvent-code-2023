var input = File.ReadAllLines("input.txt");

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

int Part1()
{
    var query =
        from line in input
        let first = line.First(Char.IsDigit) - '0'
        let last = line.Last(Char.IsDigit) - '0'
        let n = first * 10 + last
        select n;
    return query.Sum();
}

int Part2()
{
    var list = new List<int>();
    foreach (var line in input)
    {
        var digits = ExtractDigits(line).ToArray();
        var first = digits.First();
        var last = digits.Last();
        var n = first * 10 + last;
        list.Add(n);
    }

    return list.Sum();
}

List<int> ExtractDigits(string line)
{
    var start = 0;
    var end = 0;
    var digits = new List<int>();
    while (end < line.Length)
    {
        end++;
        for (int i = start; i < end; i++)
        {
            (var digit, bool back) = line.Substring(i, end - i) switch
            {
                [char d] when d >= '0' && d <= '9' => (d - '0', false),
                ['z', 'e', 'r', 'o'] => (0, true),
                ['o', 'n', 'e'] => (1, true),
                ['t', 'w', 'o'] => (2, true),
                ['t', 'h', 'r', 'e', 'e'] => (3, true),
                ['f', 'o', 'u', 'r'] => (4, false),
                ['f', 'i', 'v', 'e'] => (5, true),
                ['s', 'i', 'x'] => (6, false),
                ['s', 'e', 'v', 'e', 'n'] => (7, true),
                ['e', 'i', 'g', 'h', 't'] => (8, true),
                ['n', 'i', 'n', 'e'] => (9, true),
                _ => (-1, false)
            };
            if (digit >= 0)
            {
                digits.Add(digit);
                if (end < line.Length && back)
                    end--;
                start = end;
                break;
            }
        }
    }

    return digits;
}