import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    List<String> input = Data.get().lines().toList();
    String instructions = input.get(0);
    Map<String, Main.Pair> map = input.subList(2, input.size()).stream()
        .collect(Collectors.toMap(
            (s -> s.split(" = ")[0]),
            (s -> Pair.parse(s.split(" = ")[1]))));

    part1(instructions, map);
    part2(instructions, map);
  }

  static void part2(String instructions, Map<String, Pair> map) {
    System.out.println(map.keySet().stream()
        .filter(strt -> strt.substring(2).equals("A"))
        .map(strt -> loopUntil(instructions, map, strt, (x) -> !x.substring(2).equals("Z")))
        .reduce((a,b) -> a*b)
        .get() * instructions.length());
  }

  private static Long loopUntil(String instructions, Map<String, Pair> map, String start, Predicate<String> finalReached) {
    String current = start;
    int s = 0;
    while (finalReached.test(current)) {
      current = followInstructions(map, instructions, current);
      s += 1;
    }
    return (long) s;
  }

  static void part1(String instructions, Map<String, Pair> map) {
    System.out.println(loopUntil(instructions, map, "AAA", (x) -> !x.equals("ZZZ"))*instructions.length());
  }

  public static String followInstructions(Map<String, Pair> map, String instructions, String start) {
    String currentLocation = start;
    for (String step : instructions.split("")) {
      currentLocation = map.get(currentLocation).lr(step);
    }
    return currentLocation;
  }

  record Pair(String L, String R) {
    String lr(String lr) {
      return lr.equals("L") ? L : R;
    }

    static Pair parse(String input) {
      String[] split = input.substring(1, input.length()-1).split(", ");
      return new Pair(split[0], split[1]);
    }
  }
}
