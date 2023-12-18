import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    List<Instruction> instructions = Data.get().lines()
        .map(x -> {
          String[] line = x.split(" ");
          return new Instruction(Dir.valueOf(line[0]), Integer.valueOf(line[1]), line[2]);
        }).toList();

    part1(instructions);
    part2(instructions);
  }

  private static void part2(List<Instruction> instructions) {
    List<Instruction> transformed = instructions.stream().map(i -> i.transformColour()).toList();
    Point start = new Point(0, 0);
    List<Point> corners = new ArrayList<>();
    corners.add(start);
    for (Instruction i : transformed) {
      start = i.followOnce(start);
      corners.add(start);
    }
    long a = 0;
    long b = 0;
    for (int i = 0; i < corners.size(); i++) {
      int next = i == corners.size() - 1 ? 0 : i + 1;
      a += corners.get(i).x * corners.get(next).y - corners.get(next).x * corners.get(i).y;
      b += Math.abs(corners.get(i).x - corners.get(next).x) + Math.abs(corners.get(i).y - corners.get(next).y);
    }
    a = a / 2; // shoelace
    long i = a - b / 2 + 1;
    System.out.println(i + b);
  }

  private static void part1(List<Instruction> instructions) {
    Point start = new Point(0, 0);
    Set<Point> visited = new HashSet<>();
    for (Instruction i : instructions) {
      Result r = i.follow(start);
      start = r.last;
      visited.addAll(r.visited);
    }
    int minX = (int) visited.stream().mapToLong(v -> v.x).min().getAsLong();
    int maxX = (int) visited.stream().mapToLong(v -> v.x).max().getAsLong();
    int minY = (int) visited.stream().mapToLong(v -> v.y).min().getAsLong();
    int maxY = (int) visited.stream().mapToLong(v -> v.y).max().getAsLong();

    int count = 0;
    BorderTracker borderTracker = new BorderTracker();
    for (int y = minY; y <= maxY; y++) {
      StringBuilder sb = new StringBuilder();
      borderTracker.newRow();
      if (y == 49) {
        System.out.println("ping");
      }
      for (int x = minX; x <= maxX; x++) {
        if (visited.contains(new Point(x, y))) {
          count += 1;
          borderTracker.borderPoint(new Point(x, y), visited);
          sb.append('X');

        } else {
          count += borderTracker.isInside() ? 1 : 0;
          sb.append(borderTracker.isInside() ? 'X' : '.');
        }
      }
      System.out.println(sb.toString());
    }
    System.out.println(count);
  }

  static class BorderTracker {
    boolean inside = false;
    boolean up = false;
    boolean down = false;

    public boolean isInside() {
      return inside;
    }

    public void newRow() {
      inside = false;
      up = false;
      down = false;
    }

    public void borderPoint(Point p, Set<Point> visited) {
      if (visited.contains(Dir.U.getNext(p)) && visited.contains(Dir.D.getNext(p))) {
        inside = !inside;
      } else if (visited.contains(Dir.U.getNext(p))) {
        if (up) {
          up = false;
        } else if (down) {
          down = false;
          inside = !inside;
        } else {
          up = true;
        }
      } else if (visited.contains(Dir.D.getNext(p))) {
        if (down) {
          down = false;
        } else if (up) {
          up = false;
          inside = !inside;
        } else {
          down = true;
        }
      }
    }
  }

  record Point(long x, long y) {
  }

  record Instruction(Dir d, long a, String c) {
    public Result follow(Point start) {
      Set<Point> visited = new HashSet<>();
      Point current = start;
      for (int i = 0; i < a; i++) {
        current = d.getNext(current);
        visited.add(current);
      }
      return new Result(visited, current);
    }

    public Point followOnce(Point start) {
      return d.getNext(start, a);
    }

    public Instruction transformColour() {
      int dir = Integer.parseInt(c.substring(c.length() - 2, c.length() - 1));
      long amount = Long.parseLong(c.substring(2, c.length() - 2), 16);
      return new Instruction(Dir.values()[dir], amount, null);
    }
  }

  record Result(Set<Point> visited, Point last) {
  }

  enum Dir {
    R {
      @Override
      Point getNext(Point p, long i) {
        return new Point(p.x + i, p.y);
      }
    },
    D {
      @Override
      Point getNext(Point p, long i) {
        return new Point(p.x, p.y + i);
      }
    },
    L {
      @Override
      Point getNext(Point p, long i) {
        return new Point(p.x - i, p.y);
      }
    },
    U {
      @Override
      Point getNext(Point p, long i) {
        return new Point(p.x, p.y - i);
      }
    };

    abstract Point getNext(Point p, long i);

    Point getNext(Point p) {
      return this.getNext(p, 1);
    }
  }
}
