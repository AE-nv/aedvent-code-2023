import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) {
    Grid g = new Grid(Data.get().lines().toList());
    System.out.println(getEnergizedTiles(g, new Ray(Direction.E, new Point(0, 0))));
    
    Stream<Ray> s = IntStream.range(0, g.maxX()).mapToObj(i -> new Ray(Direction.S, new Point(i, 0)));
    Stream<Ray> n = IntStream.range(0, g.maxX()).mapToObj(i -> new Ray(Direction.N, new Point(i, g.maxY() - 1)));
    Stream<Ray> e = IntStream.range(0, g.maxY()).mapToObj(i -> new Ray(Direction.E,new Point(0, i)));
    Stream<Ray> w = IntStream.range(0, g.maxY()).mapToObj(i -> new Ray(Direction.W,new Point(g.maxY()-1, i)));
    System.out.println(Stream.concat(Stream.concat(s, n), Stream.concat(e, w))
      .mapToInt(r -> getEnergizedTiles(g, r)).max().getAsInt());
  }

  private static int getEnergizedTiles(Grid g, Ray start) {
    Queue<Ray> toHandle = new LinkedList<>();
    toHandle.add(start);
    Map<Direction, Set<Point>> map = Arrays.stream(Direction.values())
        .collect(Collectors.toMap(i -> i, i -> new HashSet<>()));

    map.get(start.d).add(start.p);

    while (toHandle.size() > 0) {
      Ray current = toHandle.remove();
      List<Ray> next = g.handle(current);
      for (Ray ray : next) {
        Set<Main.Point> set = map.get(ray.d);
        if (!set.contains(ray.p)) {
          set.add(ray.p);
          toHandle.add(ray);
        }
      }
    }
    Set<Point> result = new HashSet<>();
    map.values().forEach(s -> result.addAll(s));
    return result.size();
  }

  record Grid(List<String> g) {
    List<Ray> handle(Ray r) {
      char c = get(r.p);
      if (c == '.') {
        return getNext(r);
      }
      if (c == '|') {
        if (r.d.isVertical) {
          return getNext(r);
        } else {
          List<Ray> result = new ArrayList<>();
          result.addAll(getNext(new Ray(Direction.N, r.p)));
          result.addAll(getNext(new Ray(Direction.S, r.p)));
          return result;
        }
      }
      if (c == '-') {
        if (!r.d.isVertical) {
          return getNext(r);
        } else {
          List<Ray> result = new ArrayList<>();
          result.addAll(getNext(new Ray(Direction.E, r.p)));
          result.addAll(getNext(new Ray(Direction.W, r.p)));
          return result;
        }
      }
      if (c == '\\') {
        switch (r.d) {
          case Direction.N:
            return getNext(new Ray(Direction.W, r.p));
          case Direction.E:
            return getNext(new Ray(Direction.S, r.p));
          case Direction.S:
            return getNext(new Ray(Direction.E, r.p));
          case Direction.W:
            return getNext(new Ray(Direction.N, r.p));
        }
      }
      if (c == '/') {
        switch (r.d) {
          case Direction.N:
            return getNext(new Ray(Direction.E, r.p));
          case Direction.E:
            return getNext(new Ray(Direction.N, r.p));
          case Direction.S:
            return getNext(new Ray(Direction.W, r.p));
          case Direction.W:
            return getNext(new Ray(Direction.S, r.p));
        }
      }
      throw new IllegalArgumentException();
    }

    private List<Ray> getNext(Ray r) {
      Ray next = r.next();
      if (inBounds(next.p)) {
        return List.of(r.next());
      } else {
        return List.of();
      }
    }

    char get(Point p) {
      return g.get(p.y).charAt(p.x);
    }

    boolean inBounds(Point p) {
      return 0 <= p.y && p.y < g.size() &&
          0 <= p.x && p.x < g.get(p.y).length();
    }

    int maxX() {
      return g.get(0).length();
    }

    int maxY() {
      return g.size();
    }
  }

  record Ray(Direction d, Point p) {
    Ray next() {
      return new Ray(d, new Point(p.x + d.dx, p.y + d.dy));
    }
  }

  record Point(int x, int y) {
  }

  enum Direction {
    N(true, 0, -1),
    E(false, 1, 0),
    S(true, 0, 1),
    W(false, -1, 0);

    boolean isVertical;
    int dx;
    int dy;

    Direction(boolean isVertical, int dx, int dy) {
      this.isVertical = isVertical;
      this.dx = dx;
      this.dy = dy;
    }

  }
}
