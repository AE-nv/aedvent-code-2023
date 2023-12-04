using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var regex = Regexes.CardRegex();
var items = input.Select(s => regex.As<Card>(s)).ToImmutableArray();
var sw = Stopwatch.StartNew();
var part1 = (
    from c in items
    let e = c.NofWins
    where e > 0
    select Pow(2, e - 1)).Sum();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));
long Part2()
{
    (int nofwins, int count)[] cards = (
        from item in items
        let nofwins = item.NofWins
        select (nofwins, count: 1)).ToArray();
    for (int i = 0; i < cards.Length; i++)
    {
        var card = cards[i];
        for (var c = 0; c < card.count; c++)
        {
            foreach (var offset in Range(1, card.nofwins))
            {
                cards[i + offset].count++;
            }
        }
    }

    return cards.Sum(c => c.count);
}

record Card(int id, int[] winning, int[] numbers)
{
    public int NofWins => winning.Count(numbers.Contains);
}

static partial class Regexes
{
    [GeneratedRegex(@"^Card +(?<id>\d.*): +(?<winning>[\d ]+) \| +(?<numbers>.*)")]
    public static partial Regex CardRegex();
}