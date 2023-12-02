import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public record Game(
    int id,
    List<Round> rounds) {

  public boolean isPossible(int red, int green, int blue) {
    return rounds.stream().allMatch(x -> x.isPossible(red, green, blue));
  }

  public int getPower() {
    int red = getMax(rounds, x -> x.red());
    int green = getMax(rounds, x -> x.green());
    int blue = getMax(rounds, x -> x.blue());
    return red * green * blue;
  }

  public int getMax(List<Round> rounds, ToIntFunction<Round> getColour) {
    return rounds.stream().mapToInt(getColour).max().orElse(0);
  }

  public void print() {
    System.out.println(String.format("Game %o", id));
    rounds.forEach(x -> x.print());
  }

  public static Game parse(String line) {
    String[] split = line.split(":");
    int id = Integer.parseInt(split[0].split(" ")[1]);
    List<Round> rounds = Stream.of(split[1].split(";")).map(x -> Round.parse2(x)).toList();
    return new Game(id, rounds);
  }
}
