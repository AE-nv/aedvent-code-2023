import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    List<String> patterns = Arrays.stream(Data.get().split("\n\n")).toList();
    System.out.println(patterns.stream().mapToInt(s -> getSummary(s, (a, b) -> a.equals(b))).sum());
    System.out.println(patterns.stream().mapToInt(s -> getSummary(s, (a, b) -> differOne(a, b))).sum());
  }

  public static boolean differOne(String a, String b) {
    int count = 0;
    for (int i = 0; i < a.length(); i++) {
      if (!a.substring(i, i + 1).equals(b.substring(i, i + 1))) {
        count += 1;
      }
    }
    return count == 1;
  }

  public static int getSummary(String pattern, BiPredicate<String, String> equalityCheck) {
    int result = 0;
    List<String> p = pattern.lines().toList();
    for (int i = 1; i < p.size(); i++) {
      int size = getSize(p, i);
      String back = getVerticalBackWardString(p, i, size);
      String forward = getHorizontalForwardString(p, i, size);
      if (equalityCheck.test(back, forward)) {
        result += 100 * i;
      }
    }
    List<String> pt = transpose(p);
    for (int i = 1; i < pt.size(); i++) {
      int size = getSize(pt, i);
      String back = getVerticalBackWardString(pt, i, size);
      String forward = getHorizontalForwardString(pt, i, size);
      if (equalityCheck.test(back, forward)) {
        result += i;
      }
    }
    return result;
  }

  private static List<String> transpose(List<String> p) {
    List<String> pt = new ArrayList<>(IntStream.range(0, p.get(0).length()).mapToObj(i -> "").toList());
    for (int y = 0; y < p.size(); y++) {
      for (int x = 0; x < p.get(0).length(); x++) {
        pt.set(x, pt.get(x) + p.get(y).substring(x, x + 1));
      }
    }
    return pt;
  }

  private static String getVerticalBackWardString(List<String> p, int i, int size) {
    StringBuilder sb = new StringBuilder();
    for (int j = i - 1; j > i - size - 1; j--) {
      sb.append(p.get(j));
    }
    return sb.toString();
  }

  private static String getHorizontalForwardString(List<String> p, int i, int size) {
    StringBuilder sb = new StringBuilder();
    for (int j = i; j < i + size; j++) {
      sb.append(p.get(j));
    }
    return sb.toString();
  }

  private static int getSize(List<String> p, int i) {
    int forwardSize = p.size() - i;
    int backSize = i;
    int size = (backSize < forwardSize) ? backSize : forwardSize;
    return size;
  }
}
