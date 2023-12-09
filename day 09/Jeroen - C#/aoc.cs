var input = (
    from line in File.ReadAllLines("input.txt")
    select (
        from s in line.Split(' ') 
        select int.Parse(s)
        ).ToImmutableArray()
    ).ToImmutableArray();

var sw = Stopwatch.StartNew();

var part1 = (
    from line in input
    from value in GetValuesAt(line, ^1)
    select value
).Sum();

var part2 = (
    from line in input
    let values = GetValuesAt(line, 0).Reverse()
    select values.Skip(1).Aggregate(0, (a, b) => b - a)
).Sum();

Console.WriteLine((part1, part2, sw.Elapsed));

IEnumerable<int> GetValuesAt(IEnumerable<int> line, Index index)
{
    var sequence = line.ToList();
    yield return sequence[index];
    while (!sequence.All(i => i == 0))
    {
        for (int i = 0; i < sequence.Count - 1; i++)
        {
            sequence[i] = sequence[i + 1] - sequence[i];
        }

        sequence.RemoveAt(sequence.Count - 1);
        yield return sequence[index];
    }
}