using AdventOfCode;

var items = File.ReadAllLines("input.txt")[0].Split(',');

var sw = Stopwatch.StartNew();
var part1 = items.Select(Hash).Sum();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

int Part2()
{
    var instructions = items.Select(s => Regexes.MyRegex().As<Item>(s)).ToArray();
    var boxes = instructions.Select(x => x.Box).Distinct().ToDictionary(i => i, _ => new List<Item>());
    var query =
        from item in instructions
        let box = boxes[item.Box]
        let index = box.Select((it, idx) => (it.label, idx)).Where(p => p.label == item.label).Select(p => p.idx as int?).FirstOrDefault()
        select (box, item, index);

    foreach (var (box, item, index) in query)
    {
        switch (item.operation, index)
        {
            case ('=', not null):
                box[index.Value] = item;
                break;
            case ('=', null):
                box.Add(item);
                break;
            case ('-', not null):
                box.RemoveAt(index.Value);
                break;
        }
    }

    var result =
        from b in boxes
        from x in b.Value.Select((it, i) => (item: it, slot: i + 1))
        let lens = x.item
        select (lens.Box + 1) * x.slot * x.item.value!.Value;
    return result.Sum();
}

static int Hash(string input) => input.Aggregate(0, (c, i) => (i + c) * 17 % 256);
public record struct Item(string label, char operation, int? value)
{
    public int Box => label.Aggregate(0, (c, i) => (i + c) * 17 % 256);
}

static partial class Regexes
{
    [GeneratedRegex(@"^(?<label>[a-z]+)(?<operation>(=|-))(?<value>\d*)$")]
    public static partial Regex MyRegex();
}