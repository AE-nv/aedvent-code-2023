import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day05 {
  public static void main(String[] args) {
    String input = Data.get();
    Game game = Game.parse(input);
    System.out.println(game.getLowestSeedLocation());
    System.out.println(game.getLowestSeedLocationWithRange());
  }

  public record Range(long start, long end) {
  }

  public record Game(long[] seeds, List<Map> maps) {

    public long getLowestSeedLocation() {
      return Arrays.stream(seeds).map(s -> applyMaps(s)).min().getAsLong();
    }

    public long getLowestSeedLocationWithRange() {
      return getRanges()
          .stream()
          .flatMap(x -> applyMaps(x).stream())
          .mapToLong(r -> r.start)
          .min()
          .getAsLong();
    }

    private List<Range> getRanges() {
      List<Range> candidates = new ArrayList<>();
      for (int i = 0; i < seeds.length; i += 2) {
        candidates.add(new Range(seeds[i], seeds[i] + seeds[i + 1] - 1));
      }
      return candidates;
    }

    public long applyMaps(long seed) {
      long i = seed;
      for (Map map : maps) {
        i = map.map(i);
      }
      return i;
    }

    public List<Range> applyMaps(Range seed) {
      List<Range> ranges = List.of(seed);

      for (Map map : maps) {
        ranges = ranges.stream()
        .flatMap(r -> map.map(r).stream())
        .toList();        
      }
      return ranges;
    }

    public static Game parse(String input) {
      String[] sections = input.split("\n\n");
      long[] seeds = Stream.of(sections[0].split(":")[1].strip().split(" ")).mapToLong(v -> Long.valueOf(v)).toArray();
      List<Map> maps = new ArrayList<>();
      for (String section : Arrays.copyOfRange(sections, 1, sections.length)) {
        maps.add(Map.parse(section));
      }
      return new Game(seeds, maps);
    }
  }

  public record Map(String from, String to, List<Mapping> mappings) {

    public long map(long i) {
      return mappings.stream()
          .filter(x -> x.isRelevantFor(i))
          .map(x -> x.map(i))
          .findFirst()
          .orElse(i);
    }

    public List<Range> map(Range seed) {
      List<Range> alreadyMapped = new ArrayList<>();
      List<Range> toBeMapped = new ArrayList<>();
      toBeMapped.add(seed);
      for (Mapping mapping : mappings) {
        List<Range> tbm = new ArrayList<>();
        for (Range r : toBeMapped) {
          Mapping.Result x = mapping.map(r);
          x.getMapped().ifPresent(y -> alreadyMapped.add(y));
          tbm.addAll(x.leftOver);
        }
        toBeMapped = tbm;
      }
      alreadyMapped.addAll(toBeMapped);
      return alreadyMapped;
    }

    public static Map parse(String section) {
      List<String> lines = section.lines().toList();
      String[] fromTo = lines.get(0).split(" ")[0].split("-to-");
      List<Mapping> mappings = new ArrayList<>();
      for (int i = 1; i < lines.size(); i++) {
        mappings.add(Mapping.parse(lines.get(i)));
      }
      return new Map(fromTo[0], fromTo[1], mappings);
    }
  }

  public record Mapping(long destination, long source, long range) {

    public boolean isRelevantFor(long i) {
      return i >= source && i <= source + range - 1;
    }

    public long map(long i) {
      return destination + i - source;
    }

    public Result map(Range r) {
      if (r.end < source || r.start > sourceEnd()) {
        return new Result(List.of(r), null);
      } else if (r.start >= source && r.end <= sourceEnd()) {
        return new Result(List.of(), new Range(map(r.start), map(r.end)));
      } else if (r.start < source && r.end > sourceEnd()) {
        return new Result(List.of(
            new Range(r.start, source - 1),
            new Range(sourceEnd() + 1, r.end)), new Range(map(source), map(sourceEnd())));
      } else if (r.start < source && r.end >= source && r.end <= sourceEnd()) {
        return new Result(List.of(
            new Range(r.start, source - 1)), new Range(map(source), map(r.end)));
      } else if (r.start >= source && r.start <= sourceEnd() && r.end > sourceEnd()) {
        return new Result(List.of(
            new Range(sourceEnd() + 1, r.end)), new Range(map(r.start), map(sourceEnd())));
      }
      throw new IllegalArgumentException("this should not happen " + String.valueOf(this));
    }

    public long sourceEnd() {
      return source + range - 1;
    }

    public record Result(List<Range> leftOver, Range mapped) {
      public Optional<Range> getMapped() {
        return Optional.ofNullable(mapped);
      }
    }

    public static Mapping parse(String line) {
      long[] a = Stream.of(line.split(" ")).mapToLong(v -> Long.parseLong(v)).toArray();
      return new Mapping(a[0], a[1], a[2]);
    }
  }

  public static void test() {
    Mapping m = new Mapping(0, 10, 10);
    assert m.map(new Range(0, 9)).leftOver.get(0).equals(new Range(0, 9));
    assert m.map(new Range(0, 9)).leftOver.size() == 1;
    assert !m.map(new Range(0, 9)).getMapped().isPresent();

    assert m.map(new Range(20, 21)).leftOver.get(0).equals(new Range(20, 21));
    assert m.map(new Range(20, 21)).leftOver.size() == 1;
    assert !m.map(new Range(20, 21)).getMapped().isPresent();

    assert m.map(new Range(10, 15)).leftOver.size() == 0;
    assert m.map(new Range(10, 15)).mapped.equals(new Range(0, 5));

    assert m.map(new Range(0, 15)).leftOver.get(0).equals(new Range(0, 9));
    assert m.map(new Range(0, 15)).leftOver.size() == 1;
    assert m.map(new Range(0, 15)).mapped.equals(new Range(0, 5));

    assert m.map(new Range(15, 22)).leftOver.get(0).equals(new Range(20, 22));
    assert m.map(new Range(15, 22)).leftOver.size() == 1;
    assert m.map(new Range(15, 22)).mapped.equals(new Range(m.map(15), m.map(19)));
    assert m.map(new Range(15, 22)).mapped.equals(new Range(5, 9));

    assert m.map(new Range(5, 25)).leftOver.get(0).equals(new Range(5, 9));
    assert m.map(new Range(5, 25)).leftOver.get(1).equals(new Range(20, 25));
    assert m.map(new Range(5, 25)).leftOver.size() == 2;
    assert m.map(new Range(5, 25)).mapped.equals(new Range(m.map(10), m.map(19)));
    assert m.map(new Range(5, 25)).mapped.equals(new Range(0, 9));
  }

}
