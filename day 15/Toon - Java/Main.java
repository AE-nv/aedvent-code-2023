import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    String input = Data.get().replaceAll("\n", "");
    part1(input);
    part2(input);
  }

  private static void part2(String input) {
    List<Box> boxes = IntStream.range(0, 256)
        .mapToObj(i -> new Box(i, new ArrayList<>()))
        .toList();
    List.of(input.split(",")).stream()
        .map(s -> Command.parse(s))
        .forEach(c -> c.executeOn(boxes.get(c.hashed())));

    System.out.println(boxes.stream().mapToInt(b -> b.strength()).sum());
  }

  static abstract class Command {
    public final String label;

    Command(String label) {
      this.label = label;
    }

    abstract void executeOn(Box box);

    static Command parse(String s) {
      if (s.contains("-")) {
        return new Main.DashCommand(s.replace("-", ""));
      }
      String[] split = s.split("=");
      return new EqualCommand(split[0], Integer.parseInt(split[1]));
    }

    int hashed() {
      return hash(label);
    }
  }

  static class DashCommand extends Command {
    public DashCommand(String label) {
      super(label);
    }

    void executeOn(Box box) {
      box.lenzes.stream()
          .filter(l -> l.label.equals(this.label))
          .findFirst()
          .ifPresent(l -> box.lenzes.remove(l));
    }
  }

  static class EqualCommand extends Command {
    public final int strength;

    EqualCommand(String label, int strength) {
      super(label);
      this.strength = strength;
    }

    void executeOn(Box box) {
      box.lenzes.stream()
          .filter(l -> l.label.equals(this.label))
          .findFirst()
          .ifPresentOrElse(
              l -> {
                box.lenzes.add(box.lenzes.indexOf(l), getLens());
                box.lenzes.remove(l);
              },
              () -> box.lenzes.add(getLens()));
    }

    Lens getLens() {
      return new Lens(label, strength);
    }

  }

  record Box(int number, ArrayList<Lens> lenzes) {
    int strength() {
      return IntStream.range(0, lenzes.size())
          .map(i -> lenzes.get(i).focus * (i + 1) * (number + 1))
          .sum();
    }
  }

  record Lens(String label, int focus) {
  }

  private static void part1(String input) {
    int result = input.lines()
        .flatMap(s -> Arrays.stream(s.split(",")))
        .mapToInt(s -> hash(s))
        .sum();
    System.out.println(result);
  }

  public static int hash(String s) {
    return s.chars().reduce(0, (a, b) -> hash(a, b));
  }

  public static int hash(int current, int c) {
    return ((current + c) * 17) % 256;
  }
}
