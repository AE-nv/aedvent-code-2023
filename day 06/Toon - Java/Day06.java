import java.util.List;

public class Day06 {
  public static void main(String[] args) {
    System.out.println(part1(Data.getPart2()));
  }

  public static long part1(List<Race> races) {
    return races.stream().map(r -> r.winWays()).peek(r -> System.out.println(r)).reduce((a, b) -> a * b).get();
  }

  public record Race(long time, long distance) {
    public long winWays() {
      long d = time * time - 4 * (-1) * (0 - distance);
      if (d == 0) {
        return 1;
      } else if (d < 0) {
        return 0;
      } else {
        long r0 = (long) Math.floor(time / 2d + Math.sqrt(d) / 2d);
        long r1 = (long) Math.floor(time / 2d - Math.sqrt(d) / 2d);
        if (Math.floor(Math.sqrt(d)) - Math.sqrt(d) == 0) {
          return r0 - r1 - 1;
        }
        return r0 - r1;
      }
    }
  }
}
