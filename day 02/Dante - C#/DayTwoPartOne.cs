var data = File.ReadAllLines("input.txt");
var result = GetResult(data);
Console.WriteLine(result);

static readonly Dictionary<string, int> Bag = new()
{
    { "red", 12 }, { "green", 13 }, { "blue", 14 }
};

static int GetResult(IEnumerable<string> data) => data.Sum(GetLineScore);

static int GetLineScore(string line)
{
    if (line.Split(":", StringSplitOptions.TrimEntries) is not [string gameinfo, string grabs]) throw new Exception();
    foreach (var grab in grabs.Split(";", StringSplitOptions.TrimEntries))
    {
        var colorAndAmounts = grab.Split(",", StringSplitOptions.TrimEntries);
        foreach (var colorAndAmount in colorAndAmounts)
        {
            if (colorAndAmount.Split(" ") is not [string amountStr, string color]) throw new Exception();
            var amount = int.Parse(amountStr);
            var possible = Bag[color] >= amount;
            if (!possible) return 0;
        }
    }

    var id = gameinfo.Replace("Game ", string.Empty);
    return int.Parse(id);
}
