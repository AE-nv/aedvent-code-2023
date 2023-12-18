var data = File.ReadAllLines("input.txt");
var result = GetResult(data);
Console.WriteLine(result);

private const StringSplitOptions SplitOptions = StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries;

internal static int GetResult(IEnumerable<string> data) => data.Sum(GetScore);

private static int GetScore(string entry)
{
    var split = entry.Split(':', SplitOptions).Last().Split('|', SplitOptions);
    var winningNumbers = split.First().Split(' ', SplitOptions).Select(int.Parse).ToHashSet();
    var numbers = split.Last().Split(' ', SplitOptions).Select(int.Parse);

    var numberOfCorrectNumbers = numbers.Count(winningNumbers.Contains);
    return (int)Math.Pow(2, numberOfCorrectNumbers - 1);
}
