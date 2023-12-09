using AoCFramework;
using AoCFramework.Util;

namespace Day5;

public class Part1 : IAoCSolver
{
    const string example = @"seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4";

    public string Solve(string input)
    {
        var almanac = Almanac.Parse(example);
        var locations = almanac.Seeds.Select(almanac.GetLocationForSeed).ToList();
        return locations.Min().ToString();
    }
}

public class Almanac
{
    public const string StartType = "seed";
    public const string EndType = "location";
    public IEnumerable<AlmanacMap> TypeMappings { get; set; }
    public IEnumerable<long> Seeds { get; set; }
    public IEnumerable<IEnumerable<long>> SeedsPart2 { get; set; }
    
    public static Almanac Parse(string input)
    {
        var (seedBlock, mapBlock) = input.SplitOnDoubleNewline().Destructure();
        var typeMappings = mapBlock
            .Select(AlmanacMap.Parse);
        var almanacMaps = typeMappings.ToList();
        var seeds = seedBlock.Split("seeds: ")[1].SplitOnSpaces().Select(_ => _.ToLong()).ToList();
        var seedsPart2Grouped = seeds.GroupByNr(2);
        return new Almanac
        {
            TypeMappings = almanacMaps,
            Seeds = seeds,
            SeedsPart2 = seedsPart2Grouped
        };
    }

    public long GetLocationForSeed(long seed)
    {
        var map = GetStartMap();
        var path = new Path();
        var startValue = seed;
        while (map != null)
        {
            var destinationValue = map.GetValue(startValue);
            var pathMember = new PathMember
            {
                DestinationType = map.DestinationType,
                DestinationValue = destinationValue,
                SourceType = map.SourceType,
                SourceValue = startValue,
            };

            map = TypeMappings.FirstOrDefault(_ => _.SourceType == map.DestinationType);
            startValue = destinationValue;
            path.AddMember(pathMember);
        }

        if (!path.IsComplete())
        {
            throw new Exception("Path is not complete");
        }

        return path.End?.DestinationValue ?? throw new Exception("Path has no end");
    }

    public long GetSeedForLocation(long location)
    {
        var map = GetEndMap();
        var path = new Path();
        var destinationValue = location;
        while (map != null)
        {
            var startValue = map.GetBacktrackValue(destinationValue);
            var pathMember = new PathMember
            {
                DestinationType = map.DestinationType,
                DestinationValue = startValue,
                SourceType = map.SourceType,
                SourceValue = startValue,
            };

            map = TypeMappings.FirstOrDefault(_ => _.DestinationType == map.SourceType);
            destinationValue = startValue;
            path.AddMember(pathMember);
        }

        if (!path.IsComplete())
        {
            throw new Exception("Path is not complete");
        }

        return path.Start?.SourceValue ?? throw new Exception("Path has no start");
    }

    private AlmanacMap GetEndMap()
    {
        return TypeMappings.First(_ => _.DestinationType == EndType);
    }

    private AlmanacMap GetStartMap()
    {
        return TypeMappings.First(_ => _.SourceType == StartType);
    }

    public override string ToString()
    {
        return $"Seeds: {Seeds.ConcatBy(" ")}\n\n{TypeMappings.ConcatBy("\n\n")}";
    }

    public long GetLowestLocationNr()
    {
        return GetEndMap().Mappings.Min(_ => _.DestinationValue);
    }

    public IEnumerable<long> GetPossibleLocations()
    {
        return GetEndMap().Mappings.SelectMany(mapping => mapping.GetPossibleValues());
    }

    public bool IsPartOfSeed2(long possibleSeed)
    {
        return SeedsPart2.Any(grouping =>
        {
            var grouped = grouping.ToList();
            return possibleSeed >= grouped[0] && possibleSeed <= grouped[0] + grouped[1];
        });
    }
}

public record PathMember
{
    public long SourceValue { get; set; }
    public long DestinationValue { get; set; }
    public string SourceType { get; set; }
    public string DestinationType { get; set; }
}

public record Path
{
    public IEnumerable<PathMember> PathMembers { get; set; } = new List<PathMember>();

    public PathMember? End => PathMembers.FirstOrDefault(_ => _.DestinationType == Almanac.EndType);
    public PathMember? Start => PathMembers.FirstOrDefault(_ => _.SourceType == Almanac.StartType);

    public bool IsComplete()
    {
        return PathMembers.Any(_ => _.SourceType == Almanac.StartType) &&
               PathMembers.Any(_ => _.DestinationType == Almanac.EndType) &&
               PathMembers.Where(_ => _.SourceType != Almanac.StartType)
                   .All(pm => PathMembers.Any(_ => _.DestinationValue == pm.SourceValue)) &&
               PathMembers.Where(_ => _.DestinationType != Almanac.EndType)
                   .All(pm => PathMembers.Any(_ => _.SourceValue == pm.DestinationValue));
    }

    public void AddMember(PathMember pathMember)
    {
        PathMembers = PathMembers.Append(pathMember);
    }
}

public class AlmanacMap
{
    public string SourceType { get; set; }
    public string DestinationType { get; set; }
    public IEnumerable<Mapping> Mappings;

    public static AlmanacMap Parse(string mappingBlock)
    {
        var almanacMap = new AlmanacMap();
        var (mappingDefinitionLine, mappingLines) = mappingBlock.SplitOnNewline().Destructure();
        var (source, destination) =
            mappingDefinitionLine.Split("map:", 2,
                    StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries)[0].Split("-", 3,
                    StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries).Where(_ => _ != "to")
                .DestructureTwo();
        almanacMap.SourceType = source;
        almanacMap.DestinationType = destination;
        almanacMap.Mappings = mappingLines.Select(Mapping.FromMappingLine);
        return almanacMap;
    }

    public override string ToString()
    {
        return $"Map from {SourceType} to {DestinationType}:\n{Mappings.ConcatBy("\n")}";
    }

    public long GetValue(long inputValue)
    {
        var correspondingMapping = Mappings.FirstOrDefault(_ =>
            inputValue >= _.SourceValue && inputValue <= _.SourceValue + _.Range);
        if (correspondingMapping == null) return inputValue;
        var diff = inputValue - correspondingMapping.SourceValue;
        return correspondingMapping.DestinationValue + diff;
    }

    public long GetBacktrackValue(long outputValue)
    {
        var correspondingMapping = Mappings.FirstOrDefault(_ =>
            outputValue >= _.DestinationValue && outputValue <= _.DestinationValue + _.Range);
        if (correspondingMapping == null) return outputValue;
        var diff = outputValue - correspondingMapping.DestinationValue;
        return correspondingMapping.SourceValue + diff;
    }
}

public record Mapping(long SourceValue, long DestinationValue, long Range)
{
    public static Mapping FromMappingLine(string mappingLine)
    {
        var split = mappingLine.SplitOnSpaces();
        return new Mapping(split[1].ToLong(), split[0].ToLong(), split[2].ToLong());
    }

    public IEnumerable<long> GetPossibleValues()
    {
        return Range.Times(_ => DestinationValue + _).ToList();
    }
}