import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    List<List<List<Integer>>> subsequences = Data.get().lines()
        .map(s -> Stream.of(s.split(" "))
            .map(i -> Integer.parseInt(i)).toList())
        .map(l -> createSubsequences(l))
        .toList();
    System.out.println(part1(subsequences));
    System.out.println(part2(subsequences));
  }

  private static int part1(List<List<List<Integer>>> input) {
    return input.stream().mapToInt(l -> getNextStep(l)).sum();
  }

  private static int part2(List<List<List<Integer>>> input) {
    return input.stream().mapToInt(l -> getPreviousStep(l)).sum();
  }

  private static int getPreviousStep(List<List<Integer>> l) {
    int previous = 0;
    for (int i = l.size() - 2; i >= 0; i--) {
      int current = l.get(i).get(0);
      previous = current - previous;
    }
    return previous;
  }

  public static int getNextStep(List<List<Integer>> l) {
    return l.stream().mapToInt(i -> i.getLast()).sum();
  }

  private static List<List<Integer>> createSubsequences(List<Integer> l) {
    List<List<Integer>> subsequences = new ArrayList<>();
    subsequences.add(l);
    List<Integer> latest = l;
    while (latest.get(latest.size() - 1) != 0) {
      latest = getDifference(latest);
      subsequences.add(latest);
    }
    return subsequences;
  }

  public static List<Integer> getDifference(List<Integer> l) {
    return IntStream.range(0, l.size() - 1)
        .map(i -> l.get(i + 1) - l.get(i))
        .boxed()
        .toList();
  }
}
