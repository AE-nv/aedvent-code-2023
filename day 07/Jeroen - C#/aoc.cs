using static HandType;

var input = File.ReadAllLines("input.txt");
var sw = Stopwatch.StartNew();
var part1 = input.Select(s => Bid.Parse(s, false)).OrderBy(x => x).Select((b, index) => (long)b.Amount * (index + 1)).Sum();
var part2 = input.Select(s => Bid.Parse(s, true)).OrderBy(x => x).Select((b, index) => (long)b.Amount * (index + 1)).Sum();
Console.WriteLine((part1, part2, sw.Elapsed));

public record struct Card(char Name, int Value)
{
    public static Card From(char c, bool wildcard) => new(c, c switch
    {
        'A' => 14,
        'K' => 13,
        'Q' => 12,
        'J' => wildcard ? 0 : 11,
        'T' => 10,
        _ => c - '0'
    });
}

record Bid(Card[] Hand, int Amount, bool wildcard) : IComparable<Bid>
{
    public static Bid Parse(string s, bool wildcard)
    {
        var match = Regexes.BidRegex().Match(s);
        var hand = match.Groups["cards"].Value.Select(c => Card.From(c, wildcard)).ToArray();
        var bid = int.Parse(match.Groups["bid"].Value);
        return new Bid(hand, bid, wildcard);
    }

    public int CompareTo(Bid? other)
    {
        if (Type > other?.Type)
            return 1;
        if (Type < other?.Type)
            return -1;
        for (int i = 0; i < 5; i++)
        {
            if (Hand[i].Value > other?.Hand[i].Value)
                return 1;
            if (Hand[i].Value < other?.Hand[i].Value)
                return -1;
        }

        return 0;
    }

    public HandType Type => (wildcard, Hand.Count(c => c.Name == 'J')) switch
    {
        (true, 4 or 5) => FiveOfAKind,
        (true, 3) => CardCounts switch
        {
            [2] => FiveOfAKind,
            [1, 1] => FourOfAKind
        },
        (true, 2) => CardCounts switch
        {
            [3] => FiveOfAKind,
            [1, 2] => FourOfAKind,
            [1, 1, 1] => ThreeOfAKind,
        },
        (true, 1) => CardCounts switch
        {
            [4] => FiveOfAKind,
            [1, 3] => FourOfAKind,
            [2, 2] => FullHouse,
            [1, 1, 2] => ThreeOfAKind,
            [1, 1, 1, 1] => OnePair
        },
        _ => CardCounts switch
        {
            [5] => FiveOfAKind,
            [1, 4] => FourOfAKind,
            [2, 3] => FullHouse,
            [1, 1, 3] => ThreeOfAKind,
            [1, 2, 2] => TwoPair,
            [1, 1, 1, 2] => OnePair,
            _ => HighCard
        }
    };
    private int[] CardCounts => (
        from c in Hand
        where !wildcard || c.Name != 'J'
        group c by c into g
        let count = g.Count()
        orderby count
        select count).ToArray();
}

static partial class Regexes
{
    [GeneratedRegex(@"(?<cards>[^ ]{5}) (?<bid>\d+)")]
    public static partial Regex BidRegex();
}

public enum HandType
{
    FiveOfAKind = 7,
    FourOfAKind = 6,
    FullHouse = 5,
    ThreeOfAKind = 4,
    TwoPair = 3,
    OnePair = 2,
    HighCard = 1
}