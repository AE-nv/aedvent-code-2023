using System.Collections.Concurrent;
using AdventOfCode;

var input = File.ReadAllLines("input.txt");
var items = input.Select(s => Regexes.MyRegex().As<Item>(s)).ToImmutableArray();

var sw = Stopwatch.StartNew();
var part1 = (
    from item in items
    select Count(item.Append())).Sum();
var part2 = (
    from item in items
    select Count(item.Expand())).Sum();
Console.WriteLine((part1, part2, sw.Elapsed));

long Count(Item item) => CountImpl([], item, new(0, 0, 0));

long CountImpl(ConcurrentDictionary<Key, long> cache, Item item, Key key)
{
    if (cache.TryGetValue(key, out long count))
        return count;
    count = (item.GetCharOrDefault(key.i), key.cnt - item.numbers.Length) switch
    {
        (null, _) => item.numbers.Length == key.cnt ? 1 : 0,
        ('#', _) => CountImpl(cache, item, new(key.i + 1, key.cur + 1, key.cnt)),
        ('.', _) or (_, 0) => Recurse(cache, item, key),
        _ => CountImpl(cache, item, new(key.i + 1, key.cur + 1, key.cnt)) + Recurse(cache, item, key)
    };
    cache[key] = count;
    return count;
}

long Recurse(ConcurrentDictionary<Key, long> cache, Item item, Key key) => key.cur switch
{
    0 => CountImpl(cache, item, new(key.i + 1, 0, key.cnt)),
    _ when item.GetNumberAt(key.cnt, out var v) && v == key.cur => CountImpl(cache, item, new(key.i + 1, 0, key.cnt + 1)),
    _ => 0
};

readonly record struct Key(int i, int cur, int cnt);

readonly record struct Item(string layout, int[] numbers)
{
    public Item Append() => this with
    {
        layout = $"{layout}."
    };
    public Item Expand() => this with
    {
        layout = $"{string.Join('?', Repeat(layout, 5))}.",
        numbers = Repeat(numbers, 5).SelectMany(n => n).ToArray()
    };
    public bool GetNumberAt(int index, out int value)
    {
        if (index < numbers.Length)
        {
            value = numbers[index];
            return true;
        }

        value = -1;
        return false;
    }

    public char? GetCharOrDefault(int index)
    {
        if (index < layout.Length)
            return layout[index];
        return null;
    }

    public int Last() => numbers[numbers.Length - 1];
}

static partial class Regexes
{
    [GeneratedRegex(@"^(?<layout>[\?#\.]*) (?<numbers>[\d,]+)$")]
    public static partial Regex MyRegex();
}

