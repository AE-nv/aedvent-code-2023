var input = File.ReadAllLines("input.txt");
var games = input.Select(s => Game.Parse(s)).ToImmutableArray();

var sw = Stopwatch.StartNew();
var part1 = Part1();
var part2 = Part2();
Console.WriteLine((part1, part2, sw.Elapsed));

object Part1() => (
        from game in games
        where game.IsPossible(12, 13, 14)
        select game
        ).Sum(g => g.Id);

int Part2() =>
    (
        from game in games
        let fewest = game.Fewest()
        select fewest.red * fewest.green * fewest.blue
    ).Sum();

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
            var component = Regexes.ComponentRegex().Match(item);
            var n = int.Parse(component.Groups["n"].Value);
            (r, g, b) = component.Groups["color"].Value switch
            {
                "red" => (n, g, b),
                "green" => (r, n, b),
                "blue" => (r, g, n)
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
    [GeneratedRegex(@"^(?<n>\d+) (?<color>.+)$")]
    public static partial Regex ComponentRegex();
}

