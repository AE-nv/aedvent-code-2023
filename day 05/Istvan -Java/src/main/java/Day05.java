import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.LongStream;

import static java.lang.Character.isDigit;

public class Day05 {
    public static void main(String[] args) throws IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day05.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }


    public static Mapping createMapping(String line) {
        var parts = line.split(" ");
        return new Mapping(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
    }

    public static long partA(String file) throws IOException {
        var lines = Files.readAllLines(getNullSafePath(file));


        var seeds = lines.getFirst().split(":")[1].trim().split(" +");

        var mappings = buildMappings(lines.subList(2, lines.size()));

        return Arrays.stream(seeds)
                .map(Long::parseLong)
                .map(s -> mapSeed(s, mappings))
                .min(Long::compareTo)
                .orElseThrow();
    }

    public static long partB(String file) throws IOException {
        var lines = Files.readAllLines(getNullSafePath(file));

        record SeedRange(long start, long length){};
        var seedDefinitions = lines.getFirst().split(":")[1].trim().split(" +");
        var seeds = new ArrayList<SeedRange>(seedDefinitions.length/2);
        for (var i =0; i< seedDefinitions.length; i+=2) {
            seeds.add(new SeedRange(Long.parseLong(seedDefinitions[i]), Long.parseLong(seedDefinitions[i+1])));
        }

        var mappings = buildMappings(lines.subList(2, lines.size()));

        return seeds.stream()
                .flatMap(seedRange -> LongStream.range(seedRange.start, seedRange.start+seedRange.length).boxed())
                .map(s -> mapSeed(s, mappings))
                .min(Long::compareTo)
                .orElseThrow();
    }
    private static HashMap<String, MappingSet> buildMappings(List<String> lines) {
        var mappings = new HashMap<String, MappingSet>();
        MappingSet currSet = null;
        for (var line : lines) {
            if (line.isBlank())
                mappings.put(currSet.sourceType(), currSet);
            else if (Character.isDigit(line.charAt(0)))
                currSet.addMapping(createMapping(line));
            else {
                var parts = line.split("(-| )");
                currSet = new MappingSet(parts[0],parts[2]);
            }
        }
        mappings.put(currSet.sourceType(), currSet);
        return mappings;
    }

    public static long mapSeed(long seed, Map<String, MappingSet> mappings) {
        String type = "seed";
        long mappedValue = seed;
        while (!"location".equals(type)) {
            var set = mappings.get(type);
            mappedValue = set.map(mappedValue);
            type = set.destinationType();

        }
        return mappedValue;
    }

    public record Mapping(long destination, long source, long length){
        public long map(long sourceToMap) {
            if (sourceToMap < source || sourceToMap > (source + length -1))
                return -1;
            return destination + (sourceToMap-source);
        }
    }

    public static class MappingSet {
        private final String sourceType;
        private final String destinationType;

        private final List<Mapping> mappings = new LinkedList<>();
        public MappingSet(String sourceType, String destinationType) {
            this.sourceType = sourceType;
            this.destinationType = destinationType;
        }

        public String sourceType() {
            return sourceType;
        }

        public String destinationType() {
            return destinationType;
        }

        public void addMapping(Mapping mapping) {
            this.mappings.add(mapping);
        }

        public long map(long value) {
            for(var mapping: mappings){
                long mapped = mapping.map(value);
                if (mapped > -1)
                    return mapped;
            }
            return value;
        }
    }
}