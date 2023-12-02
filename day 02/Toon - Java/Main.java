import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<Game> games = Data
        .getData()
        .lines()
        .map(x -> Game.parse(x))
        // .peek(x -> x.print())
        .toList();
    part1(games);
    part2(games);
  }

  public static Game print(Game game) {
    game.print();
    return game;
  }

  public static void part1(List<Game> games) {
    System.out.println(games.stream()
        .filter(x -> x.isPossible(12, 13, 14))
        .mapToInt(x -> x.id())
        .sum());
  }

  public static void part2(List<Game> games) {
    System.out.println(games.stream()
        .map(x -> x.getPower())
        .mapToInt(x -> x)
        .sum());
  }
}
