using AoCFramework.Util;

namespace Day2;

public class Game
{
    public static Game Parse(string input)
    {
        var gameSection = input.Split(":")[0];
        var handsSection = input.Split(":")[1];
        var hands = handsSection.Split(";").Select(Set.Parse);
        var gameNr = int.Parse(gameSection.Split(" ")[1]);
        return new Game(gameNr).AddHands(hands);
    }

    private Game AddHands(IEnumerable<Set> hands)
    {
        Hands = Hands.Concat(hands);
        return this;
    }

    public bool AllHandsPossibleGivenBag(IDictionary<string, int> bag)
    {
        return Hands.All(hand => hand.PossibleGivenBag(bag));
    }

    public Game(int gameNr)
    {
        GameNr = gameNr;
    }

    public Game AddHand(Set set)
    {
        Hands = Hands.Append(set);
        return this;
    }

    public int GameNr { get; set; }
    public IEnumerable<Set> Hands { get; set; } = Array.Empty<Set>();

    public override string ToString()
    {
        return $"Game {GameNr}: {string.Join("; ", Hands)}";
    }

    public Set MinimalSet()
    {
        return Hands.SelectMany(hand => hand.CubeCount.Keys.Select(k => k)).ToHashSet()
            .Select(color => (color, Hands.Select(_ => _.CubeCount.TryOrFallback(color,0)).Max()))
            .ToSet();
    }
}

internal static class GameExtension
{
    public static Set ToSet(this IEnumerable<(string, int)> entries)
    {
        return new Set { CubeCount = entries.ToDictionary(_ => _.Item1, _ => _.Item2) };
    }
}

public class Set
{
    public Dictionary<string, int> CubeCount { get; set; }

    public static Set Parse(string arg)
    {
        var cubeCount = arg
            .Split(",")
            .Select(_ => _.Trim().Split(" "))
            .ToDictionary(_ => _[1], _ => int.Parse(_[0]));
        return new Set { CubeCount = cubeCount };
    }

    public int Power()
    {
        return CubeCount.Select(_ => _.Value).Product();
    }

    public override string ToString()
    {
        return string.Join(", ", CubeCount.Select(_ => $"{_.Value} {_.Key}"));
    }

    public bool PossibleGivenBag(IDictionary<string, int> bag)
    {
        return CubeCount.All(_ => bag.ContainsKey(_.Key) && bag[_.Key] >= _.Value);
    }
}