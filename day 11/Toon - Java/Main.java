import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    List<List<String>> map = Data.get()
        .lines()
        .map(s -> Arrays.asList(s.split(""))).toList();

    List<List<String>> expanded = expand(map);

    List<Galaxy> galaxies = getGalaxies(expanded);

    System.out.println(String.format("Part 1: %s", distances(galaxies)));
    
    long expansionRate = 1000000;
    System.out.println(String.format("Part 2: %s", distances(expand(map, getGalaxies(map), expansionRate - 1))));
  }

  private static List<Galaxy> expand(List<List<String>> map, List<Galaxy> galaxies, long expansionRate) {
    List<Integer> emptyVertical = getEmptyVerticals(map);
    List<Integer> emptyHorizontal = getEmptyHorizontal(map);
    return galaxies.stream()
        .map(g -> new Galaxy(g.x + expansionRate * countBefore(g.x, emptyVertical),
                             g.y + expansionRate * countBefore(g.y, emptyHorizontal)))
        .toList();
  }

  private static long countBefore(long galaxy, List<Integer> list) {
    return list.stream().filter(i -> i < galaxy).count();
  }

  private static long distances(List<Galaxy> galaxies) {
    long distance = 0;
    for (int i = 0; i < galaxies.size() - 1; i++) {
      Galaxy current = galaxies.get(i);
      distance += galaxies.subList(i + 1, galaxies.size()).stream()
          .mapToLong(g -> Math.abs(g.x - current.x) + Math.abs(g.y - current.y))
          .sum();
    }
    return distance;
  }

  private static List<Galaxy> getGalaxies(List<List<String>> expanded) {
    List<Galaxy> galaxies = new ArrayList<>();
    for (int y = 0; y < expanded.size(); y++) {
      for (int x = 0; x < expanded.get(y).size(); x++) {
        if (expanded.get(y).get(x).equals("#")) {
          galaxies.add(new Galaxy(x, y));
        }
      }
    }
    return galaxies;
  }

  private static List<List<String>> expand(List<List<String>> map) {
    List<Integer> emptyVertical = getEmptyVerticals(map);
    List<Integer> emptyHorizontal = getEmptyHorizontal(map);

    List<List<String>> newMap = new ArrayList<>();
    for (int y = 0; y < map.size(); y++) {
      List<String> row = new ArrayList<>();
      for (int x = 0; x < map.get(y).size(); x++) {
        row.add(map.get(y).get(x));
        if (emptyVertical.contains(x)) {
          row.add(".");
        }
      }
      newMap.add(row);
      if (emptyHorizontal.contains(y)) {
        newMap.add(row);
      }
    }
    return newMap;
  }

  private static List<Integer> getEmptyHorizontal(List<List<String>> map) {
    List<Integer> emptyHorizontal = new ArrayList<>();
    for (int y = 0; y < map.size(); y++) {
      if (!map.get(y).contains("#")) {
        emptyHorizontal.add(y);
      }
    }
    return emptyHorizontal;

  }

  private static List<Integer> getEmptyVerticals(List<List<String>> map) {
    List<Integer> emptyVertical = new ArrayList<>();
    for (int x = 0; x < map.get(0).size(); x++) {
      if (!getVertical(map, x).contains("#")) {
        emptyVertical.add(x);
      }
    }
    return emptyVertical;

  }

  public static String getVertical(List<List<String>> map, int x) {
    StringBuilder sb = new StringBuilder();
    for (List<String> list : map) {
      sb.append(list.get(x));
    }
    return sb.toString();
  }

  record Galaxy(long x, long y) {
  }

}
