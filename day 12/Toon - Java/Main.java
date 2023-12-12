import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    System.out.println(Data.get().lines()
        .map(s -> Line.parse(s))
        .mapToInt(s -> s.getVariantCount())
        .peek(s -> System.out.println(s))
        .sum());
    
    System.out.println(Data.get().lines()
        .map(s -> Line.parse2(s))
        .peek(s->System.out.println(s))
        .mapToLong(s -> s.getCached())
        .peek(s -> System.out.println(s))
        .sum());
  }

  record Line(String row, List<Integer> groups) {
    static Map<String, Long> cache = new HashMap<>();
    public int getVariantCount() {
      if (row.contains("?")) {
        return new Line(row.replaceFirst("\\?", "."), groups).getVariantCount()
            + new Line(row.replaceFirst("\\?", "#"), groups).getVariantCount();
      }

      List<Integer> list = Stream.of(row.split("\\."))
          .filter(s -> s.length() > 0)
          .map(s -> s.length()).toList();
      if (list.size() != groups.size()) {
        return 0;
      }
      for (int i = 0; i < groups.size(); i++) {
        if (list.get(i) != groups.get(i)) {
          return 0;
        }
      }
      return 1;
    }

    public long getCached() {
      String key = this.toString();
      if (cache.containsKey(key)) {
        return cache.get(key);
      }
      long count = getVariantCountQuick();
      cache.put(key, count);
      return count;
    }

    public long getVariantCountQuick() {
      if (row.length() == 0) {
        return groups.isEmpty() ? 1 : 0;
      }

      if (row.length() < groups.stream().mapToInt(i -> i + 1).sum()-1) {
        return 0;
      }

      if (row.startsWith(".")) {
        return new Line(row.substring(1), groups).getCached();
      }

      // starts with # or ?
      if (row.startsWith("?")) {
        return new Line(row.substring(1), groups).getCached() // ., but skipping one useless step
          + new Line("#" + row.substring(1), groups).getCached(); // #
      }
      
      if (groups.isEmpty()) {
        return 0;
      }

      // starts with #
      if (row.length() < groups.get(0)) {
        return 0;
      }
      String substring = row.substring(0, groups.get(0));
      if (substring.contains(".")) {
        return 0;
      } else {
        String newRow = row.substring(groups.get(0));
        if (newRow.startsWith("#")) {
          return 0;
        } else if (newRow.length() == 0) {
          return new Line("", groups.subList(1, groups.size())).getCached();
        } else {
          return new Line(row.substring(groups.get(0)+1), groups.subList(1, groups.size())).getCached();
        }
      }
    }

    public static Line parse(String s) {
      String[] split = s.split(" ");
      List<Integer> groups = Stream.of(split[1].split(",")).map(i -> Integer.parseInt(i)).toList();
      return new Line(split[0], groups);
    }

    public static Line parse2(String s) {
      Line first = Line.parse(s);
      ArrayList<Integer> list = new ArrayList<>();
      list.addAll(first.groups);
      list.addAll(first.groups);
      list.addAll(first.groups);
      list.addAll(first.groups);
      list.addAll(first.groups);
      
      return new Line(first.row +"?"+ first.row +"?"+ first.row +"?"+ first.row +"?"+ first.row, list);
    }
  }
}
