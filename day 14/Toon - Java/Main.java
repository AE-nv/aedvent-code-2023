import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<String> originalMap = Data.get().lines().toList();
    List<String> transposed = transpose(originalMap);
    part1(transposed);
    part2(transposed);
  }

  private static void part2(List<String> grid) {
    long count = 0;
    ArrayList<String> results = new ArrayList<>();
    String key = serialize(grid);
    while(!results.contains(key)) {
      results.add(key);
      count += 1;
      grid = cycle(grid);
      key = serialize(grid);
      if (count % 1000 == 0) {
        System.out.println(String.format("Beep: %s", count));
      }
    }
    long cycle = (count - results.indexOf(key));
    long remainder = (1000000000 - count) % cycle; 
    for (int i = 0; i < remainder; i++) {
      grid = cycle(grid);
    }
    getScore(grid);
  }

  private static String serialize(List<String> grid) {
    return grid.stream().reduce((a,b) -> a+b).get();
  }

  private static void print(List<String> grid) {
    grid.stream().peek(i->System.out.println(i)).toList();
  }

  private static List<String> cycle(List<String> grid) {
    List<String> nextState = grid;
    for (int i = 0; i < 4; i++) {
      nextState = nextState.stream().map(s -> fallBoulders(s)).toList();
      nextState = rotateCounterClockwise(nextState);
    }
    return nextState;
  }

  private static List<String> rotateCounterClockwise(List<String> grid) {
    List<String> result = new ArrayList<>();
    for (int w = 0; w < grid.get(0).length(); w++) {
      StringBuilder sb = new StringBuilder();
      for (int z = 0; z < grid.size(); z++) {
        sb.append(grid.get(z).charAt(grid.get(0).length() - w-1));
      }
      result.add(sb.toString());
    }
    return result.stream().map(sb -> sb.toString()).toList();
  }

  private static void part1(List<String> transposed) {
    List<String> fallenBoulders = transposed.stream().map(s -> fallBoulders(s)).toList();
    getScore(fallenBoulders);
  }

  private static void getScore(List<String> fallenBoulders) {
    int count = 0;
    for (String row : fallenBoulders) {
      int colCount = getScore(row);
      count += colCount;
    }
    System.out.println(count);
  }

  private static int getScore(String sortedColumn) {
    int colCount = 0;
    for (int y = 0; y < sortedColumn.length(); y++) {
      if (sortedColumn.charAt(y) == 'O') {
        colCount += sortedColumn.length() - y;
      }
    }
    return colCount;
  }

  private static String fallBoulders(String c) {
    String column = c;
    if (column.charAt(column.length() - 1) == '#') {
      column = c + ".";
    }
    String[] sections = column.split("#");
    StringBuilder sb = new StringBuilder();
    for (String section : sections) {
      int length = section.length();
      String newSection = section.replace(".", "");
      sb.append(newSection + ".".repeat(length - newSection.length()) + "#");
    }
    String sortedColumn = sb.toString().substring(0, c.length());
    return sortedColumn;
  }

  private static List<String> transpose(List<String> originalMap) {
    List<StringBuilder> transposedBuilder = new ArrayList<>();
    for (int y = 0; y < originalMap.size(); y++) {
      for (int x = 0; x < originalMap.get(y).length(); x++) {
        if (transposedBuilder.size() <= x) {
          transposedBuilder.add(new StringBuilder());
        }
        transposedBuilder.get(x).append(originalMap.get(y).charAt(x));
      }
    }
    List<String> transposed = transposedBuilder.stream().map(f -> f.toString()).toList();
    return transposed;
  }
}
