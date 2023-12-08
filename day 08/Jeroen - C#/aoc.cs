using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var steps = input[0];
var dictionary = input[2..].Select(s => Regexes.MyRegex().As<Item>(s)).ToImmutableDictionary(x => x.name);
var sw = Stopwatch.StartNew();
var part1 = CalculateSteps(dictionary, "AAA", "ZZZ");
var part2 = dictionary.Keys.Where(x => x[2] == 'A').Select(n => CalculateSteps(dictionary, n, "Z")).LeastCommonMultiplier();
Console.WriteLine((part1, part2, sw.Elapsed));
int CalculateSteps(IReadOnlyDictionary<string, Item> nodes, string start, string end)
{
    int i = 0;
    string node = start;
    while (!node.EndsWith(end))
    {
        var step = steps[i % steps.Length];
        node = step switch
        {
            'R' => nodes[node].right,
            'L' => nodes[node].left
        };
        i++;
    }

    return i;
}

readonly record struct Item(string name, string left, string right);
static partial class Regexes
{
    [GeneratedRegex(@"^(?<name>.{3}) = \((?<left>.{3}), (?<right>.{3})\)$")]
    public static partial Regex MyRegex();
}