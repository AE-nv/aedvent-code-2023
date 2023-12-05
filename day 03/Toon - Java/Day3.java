import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 {
  public static void main(String[] args) {
    String input = Data.get();
    part1(input);
    part2(input);
  }

  public static void part1(String input) {
    List<String> lines = input.lines().toList();
    List<Integer> numbers = new ArrayList<>();
    for (int y = 0; y < lines.size(); y++) {

      String line = lines.get(y);
      String intermediate = "";

      for (int x = 0; x < line.length(); x++) {
        if (Character.isDigit(line.charAt(x))) {
          intermediate = intermediate + line.substring(x, x + 1);
          if (x == line.length() - 1) {
            handleNumber(numbers, lines, y, x, intermediate);
          }
        } else if (intermediate.length() > 0) {
          handleNumber(numbers, lines, y, x, intermediate);
          intermediate = "";
        }
      }
    }

    System.out.println(numbers.stream().mapToInt(i -> i).sum());
  }

  public static void handleNumber(List<Integer> numbers, List<String> lines, int y, int x, String number) {
    if (touchesSymbol(lines, y, x, number)) {
      numbers.add(Integer.parseInt(number));
    }
  }

  public static boolean touchesSymbol(List<String> lines, int y, int x, String number) {
    String toCheck = "";
    String lineOf = lines.get(y);
    int maxX = lineOf.length();
    int fromX = bringInBounds(x - number.length() - 1, maxX);
    int toX = bringInBounds(x + 1, maxX);

    if (y > 0) {
      toCheck += lines.get(y - 1).substring(fromX, toX);
    }
    toCheck += lines.get(y).substring(fromX, toX);
    if (y < lines.size() - 1) {
      toCheck += lines.get(y + 1).substring(fromX, toX);
    }

    return toCheck.replaceAll("[0-9.]", "").length() > 0;
  }

  public static int bringInBounds(int x, int maxX) {
    if (x < 0) {
      return 0;
    } else if (x > maxX) {
      return maxX;
    }
    return x;
  }

  public static void part2(String input) {
    Map<String, List<Integer>> gears = new HashMap<>();

    List<String> lines = input.lines().toList();
    for (int y = 0; y < lines.size(); y++) {

      String line = lines.get(y);
      StringBuilder numberBuilder = new StringBuilder();

      for (int x = 0; x < line.length(); x++) {
        if (Character.isDigit(line.charAt(x))) {
          numberBuilder.append(line.substring(x, x + 1));
          if (x == line.length() - 1) {
            handleNumber(gears, lines, y, x, numberBuilder.toString());
          }
        } else if (numberBuilder.length() > 0) {
          handleNumber(gears, lines, y, x, numberBuilder.toString());
          numberBuilder = new StringBuilder();
        }
      }
    }

    System.out.println(gears.entrySet()
        .stream()
        .filter(e -> e.getValue().size() == 2)
        .map(e -> e.getValue().stream().reduce((a, b) -> a * b).get())
        .reduce((a, b) -> a + b).get());
  }

  public static void handleNumber(Map<String, List<Integer>> gears, List<String> lines, int y, int x, String number) {
    String lineOf = lines.get(y);
    int maxX = lineOf.length();
    int fromX = bringInBounds(x - number.length() - 1, maxX);
    int toX = bringInBounds(x + 1, maxX);
    if (y > 0) {
      checkAndHandleGear(gears, lines, y - 1, number, fromX, toX);
    }
    checkAndHandleGear(gears, lines, y, number, fromX, toX);
    if (y < lines.size() - 1) {
      checkAndHandleGear(gears, lines, y + 1, number, fromX, toX);
    }
  }

  private static void checkAndHandleGear(Map<String, List<Integer>> gears, List<String> lines, int y, String number,
      int fromX,
      int toX) {
    int position = lines.get(y).substring(fromX, toX).indexOf("*");
    if (position != -1) {
      addToMap(gears, y, fromX + position, number);
    }
  }

  public static void addToMap(Map<String, List<Integer>> gears, int y, int x, String number) {
    String key = String.valueOf(y) + "~" + String.valueOf(x);
    List<Integer> list = gears.getOrDefault(key, new ArrayList<>());
    list.add(Integer.parseInt(number));
    gears.put(key, list);
  }

}
