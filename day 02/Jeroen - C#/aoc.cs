var input = File.ReadAllLines("input.txt");
var games = input.Select(s => Game.Parse(s)).ToImmutableArray();

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

object Part1()
{
    var (red, green, blue) = (12, 13, 14);
    var possible =
        from game in games
        where game.IsPossible(red, green, blue)
        select game;
    return possible.Sum(g => g.Id);
}

object Part2()
{
    var query =
        from game in games
        let fewest = game.Fewest()
        select fewest.red * fewest.green * fewest.blue;
    return query.Sum();
}

readonly record struct Game(int Id, Grab[] Grabs)
{
    public bool IsPossible(int red, int green, int blue) => Grabs.All(gr => gr.IsPossible(red, green, blue));
    public (int red, int green, int blue) Fewest() => (Grabs.Max(g => g.Red), Grabs.Max(g => g.Green), Grabs.Max(g => g.Blue));
    public static Game Parse(string s)
    {
        var m = Regexes.GameRegex().Match(s);
        return new Game(int.Parse(m.Groups["id"].Value), m.Groups["grabs"].Value.Split("; ").Select(Grab.Parse).ToArray());
    }

    public override string ToString() => $"Game {Id}: {string.Join("; ", Grabs)}";
}

readonly record struct Grab(int Red, int Green, int Blue)
{
    public bool IsPossible(int red, int green, int blue) => red >= Red && green >= Green && blue >= Blue;
    public static Grab Parse(string s)
    {
        var split = s.Split(", ");
        int r = 0, g = 0, b = 0;
        foreach (var item in split)
        {
            var components = item.Split(' ');
            (r, g, b) = components[1] switch
            {
                "red" => (int.Parse(components[0]), g, b),
                "green" => (r, int.Parse(components[0]), b),
                "blue" => (r, g, int.Parse(components[0]))
            };
        }

        return new(r, g, b);
    }

    public override string ToString() => $"{Red} red, {Green} green, {Blue} blue";
}

static partial class Regexes
{
    [GeneratedRegex(@"^Game (?<id>\d+): (?<grabs>.+)$")]
    public static partial Regex GameRegex();
}

