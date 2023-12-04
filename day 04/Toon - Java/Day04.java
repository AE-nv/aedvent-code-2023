import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {
  public static void main(String[] args) {
    List<Card> input = Data.get()
        .lines()
        .map(x -> Card.parse(x)).toList();
    part1(input);
    part2(input);
  }

  public static void part1(List<Card> input) {
    System.out.println(input.stream()
        .map(c -> c.points())
        .reduce((a, b) -> a + b)
        .get());
  }

  public static void part2(List<Card> input) {
    Map<Integer, Integer> winMap = input.stream().collect(Collectors.toMap(x -> x.id(), x -> (int) x.wins()));
    Map<Integer, Integer> resultMap = winMap.keySet().stream().collect(Collectors.toMap(x -> x, x -> 1));
    for (int id = 1; id < winMap.entrySet().size(); id++) {
      int amountOfCards = resultMap.get(id);
      int wins = winMap.get(id);
      for (int i = id + 1; i <= id + wins; i++) {
        int r = resultMap.getOrDefault(i, 0);
        resultMap.put(i, r + amountOfCards);
      }
    }
    System.out.println(resultMap.values().stream().reduce((a, b) -> a + b).get());
  }

  public record Card(int id, List<Integer> winning, List<Integer> youHave) {

    public int wins() {
      return (int) youHave.stream().filter(x -> winning.contains(x)).count();
    }

    public int points() {
      int wins = wins();
      return wins > 0 ? (int) Math.pow(2, wins - 1) : 0;
    }

    public static Card parse(String line) {
      String[] cardNumbers = line.split(":");
      int id = Integer.parseInt(cardNumbers[0].replace("  ", " ").replace("  ", " ").split(" ")[1]);
      String[] numbers = cardNumbers[1].split("\\|");
      return new Card(id, toIntegerList(numbers[0]), toIntegerList(numbers[1]));
    }
  }

  public static List<Integer> toIntegerList(String part) {
    return Stream.of(part.replace("  ", " ").strip().split(" "))
        .map(i -> Integer.parseInt(i))
        .toList();
  }
}
