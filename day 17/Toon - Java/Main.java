import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

public class Main {

  public static void main(String[] args) {
    Grid grid = new Grid(Data.get().lines()
        .map(l -> Arrays.stream(l.split(""))
            .map(s -> Integer.parseInt(s)).toList())
        .toList());
    System.out.println(getPath(new Point(0, 0), new Point(grid.maxX(), grid.maxY()), grid));
  }

  public static int getPath(Point start, Point goal, Grid g) {
    Map<Movement, Movement> cameFrom = new HashMap<>();
    Map<Movement, Integer> gScore = new HashMap<>();
    Movement startMovement = new Movement(start, Dir.E, 0);
    gScore.put(startMovement, 0);
    Movement startMovement2 = new Movement(start, Dir.S, 0);
    gScore.put(startMovement2, 0);
    PriorityQueue<Movement> queue = new PriorityQueue<>(
        (a, b) -> Integer.compare(gScore.get(a) + g.get(a.p), gScore.get(b) + g.get(b.p)));
    queue.add(startMovement);
    queue.add(startMovement2);
    while (!queue.isEmpty()) {
      Movement current = queue.remove();
      if (current.p.equals(goal)) {
        Movement previous = cameFrom.get(current);
        while (!previous.p.equals(start)) {
          System.out.println(previous);
          previous = cameFrom.get(previous);
        }
        return gScore.get(current);
      }
      for (Movement neighbour : g.getNeighbours(current)) {
        int tentativeScore = gScore.get(current) + g.get(neighbour.p);
        if (tentativeScore < gScore.getOrDefault(neighbour, Integer.MAX_VALUE)) {
          cameFrom.put(neighbour, current);
          gScore.put(neighbour, tentativeScore);
          if (!queue.contains(neighbour)) {
            queue.add(neighbour);
          }
        }
      }
    }
    return 0;
  }

  record Grid(List<List<Integer>> g) {
    public List<Movement> getNeighbours(Movement p) {
      List<Movement> result = new ArrayList<>();
      result.addAll(p.getLeftRight());
      p.getNext().ifPresent(i -> result.add(i));
      return result.stream().filter(i -> inBounds(i.p)).toList();
    }

    public int get(Point p) {
      return g.get(p.y).get(p.x);
    }

    public int maxY() {
      return g.size() - 1;
    }

    public int maxX() {
      return g.get(0).size() - 1;
    }

    boolean inBounds(Point p) {
      return 0 <= p.y && p.y < g.size() &&
          0 <= p.x && p.x < g.get(p.y).size();
    }
  }

  record Movement(Point p, Dir d, int steps) {
    List<Movement> getLeftRight() {
      switch (d) {
        case N:
          return List.of(new Movement(new Point(p.x + 1, p.y), Dir.E, 0),
              new Movement(new Point(p.x - 1, p.y), Dir.W, 0));
        case S:
          return List.of(new Movement(new Point(p.x + 1, p.y), Dir.E, 0),
              new Movement(new Point(p.x - 1, p.y), Dir.W, 0));
        case E:
          return List.of(new Movement(new Point(p.x, p.y - 1), Dir.N, 0),
              new Movement(new Point(p.x, p.y + 1), Dir.S, 0));
        case W:
          return List.of(new Movement(new Point(p.x, p.y - 1), Dir.N, 0),
              new Movement(new Point(p.x, p.y + 1), Dir.S, 0));
      }
      throw new IllegalArgumentException();
    }

    Optional<Movement> getNext() {
      if (steps < 2) {
        switch (d) {
          case N:
            return Optional.of(new Movement(new Point(p.x, p.y - 1), d, steps + 1));
          case E:
            return Optional.of(new Movement(new Point(p.x + 1, p.y), d, steps + 1));
          case S:
            return Optional.of(new Movement(new Point(p.x, p.y + 1), d, steps + 1));
          case W:
            return Optional.of(new Movement(new Point(p.x - 1, p.y), d, steps + 1));
        }
      }
      return Optional.empty();
    }
  }

  record Point(int x, int y) {

    public List<Point> getLeftRightFront(Point previous) {
      return List.of(new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1), new Point(x, y + 1))
          .stream()
          .filter(p -> p != previous)
          .toList();
    }

    public List<Point> getLeftRight(Point previous) {
      if (previous.x == x) {
        return List.of(new Point(x - 1, y), new Point(x + 1, y));
      } else {
        return List.of(new Point(x, y - 1), new Point(x, y + 1));
      }
    }

    public boolean areInLine(Point previous, Point previous2, Point previous3) {
      if (previous2 == null || previous == null || previous3 == null) {
        return false;
      } else if (x == previous.x && previous.x == previous2.x && previous2.x == previous3.x) {
        return true;
      } else if (y == previous.y && previous.y == previous2.y && previous2.y == previous3.y) {
        return true;
      }
      return false;
    }
  }

  enum Dir {
    N,
    E,
    S,
    W;
  }

}
