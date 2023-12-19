import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    String[] sections = Data.get().split("\n\n");
    Map<String, Workflow> workflows = sections[0].lines()
        .collect(Collectors.toMap(
            x -> x.split("\\{")[0],
            x -> Workflow.parse(x)));
    part1(sections, workflows);
    part2(workflows);
  }

  private static void part2(Map<String, Workflow> workflows) {
    Part2 start = new Part2(
        Map.of("x", 1,
            "m", 1,
            "a", 1,
            "s", 1),
        Map.of("x", 4000,
            "m", 4000,
            "a", 4000,
            "s", 4000));
    Workflow w1 = workflows.get("in");
    List<Part2> result = w1.handle(start, workflows);
    System.out.println(result.stream().mapToLong(p -> p.posibilities()).sum());
  }

  private static void part1(String[] sections, Map<String, Workflow> workflows) {
    int result = sections[1].lines()
        .map(i -> Part.parse(i))
        .filter(p -> workflows.get("in").handle(p, workflows).equals("A"))
        .mapToInt(p -> p.sum())
        .sum();
    System.out.println(result);
  }

  record Workflow(List<Rule> rules) {

    String handle(Part p, Map<String, Workflow> workflows) {
      for (Rule rule : rules) {
        if (rule.matches(p)) {
          if (workflows.containsKey(rule.destination)) {
            return workflows.get(rule.destination).handle(p, workflows);
          }
          return rule.destination;
        }
      }
      throw new IllegalArgumentException();
    }

    List<Part2> handle(Part2 p, Map<String, Workflow> workflows) {
      List<Part2> result = new ArrayList<>();
      for (Rule rule : rules) {
        Rule.Result split = rule.split(p, workflows);
        result.addAll(split.result);
        if (split.toBeEvaluated != null) {
          p = split.toBeEvaluated;
        }
      }
      return result;
    }

    static Workflow parse(String s) {
      return new Workflow(
          Arrays.stream(s.split("\\{")[1].split("\\}")[0].split(","))
              .map(x -> Rule.parse(x))
              .toList());
    }
  }

  record Rule(String rating, Condition condition, int threshHold, String destination) {

    boolean matches(Part p) {
      if (rating == null) {
        return true;
      }
      return condition.check(p.get(rating), threshHold);
    }

    Result split(Part2 p, Map<String, Workflow> workflows) {
      if (rating == null) {
        return assembleResult(workflows, p, null);
      }
      Part2 toHandle;
      Part2 toBeEvaluated;

      if (condition == Condition.LESS) {
        Map<String, Integer> newMinRatings = p.minRatings.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        newMinRatings.replace(rating, threshHold);
        Map<String, Integer> newMaxRatings = p.maxRatings.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        newMaxRatings.replace(rating, threshHold - 1);
        toHandle = new Part2(p.minRatings, newMaxRatings);
        toBeEvaluated = new Part2(newMinRatings, p.maxRatings);
      } else { // Condition.GREATER
        Map<String, Integer> newMinRatings = p.minRatings.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        newMinRatings.replace(rating, threshHold + 1);
        Map<String, Integer> newMaxRatings = p.maxRatings.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        newMaxRatings.replace(rating, threshHold);
        toHandle = new Part2(newMinRatings, p.maxRatings);
        toBeEvaluated = new Part2(p.minRatings, newMaxRatings);
      }

      return assembleResult(workflows, toHandle, toBeEvaluated);
    }

    private Result assembleResult(Map<String, Workflow> workflows, Part2 toHandle, Part2 toBeEvaluated) {
      if (destination.equals("A")) {
        return new Result(List.of(toHandle), toBeEvaluated);
      } else if (destination.equals("R")) {
        return new Result(List.of(), toBeEvaluated);
      } else {
        return new Result(workflows.get(destination).handle(toHandle, workflows), toBeEvaluated);
      }
    }

    record Result(List<Part2> result, Part2 toBeEvaluated) {
    }

    static Rule parse(String s) {
      if (s.contains("<") || s.contains(">")) {
        return new Rule(s.substring(0, 1),
            Condition.parse(s.substring(1, 2)),
            Integer.parseInt(s.substring(2, s.indexOf(":"))),
            s.substring(s.indexOf(":") + 1));
      }
      return new Rule(null, null, 0, s);
    }

  }

  record Part(Map<String, Integer> ratings) {
    int get(String s) {
      return ratings.get(s);
    }

    int sum() {
      return ratings.values().stream().mapToInt(i -> i).sum();
    }

    static Part parse(String s) {
      return new Part(Arrays.stream(s.replaceAll("[{}]", "").split(","))
          .map(r -> r.split("="))
          .collect(Collectors.toMap(r -> r[0], r -> Integer.parseInt(r[1]))));

    }
  }

  record Part2(Map<String, Integer> minRatings, Map<String, Integer> maxRatings) {
    int getMin(String s) {
      return minRatings.get(s);
    }

    public void print() {
      System.out.println(String.format("x:%s-%s m: %s-%s a: %s-%s s: %s-%s",
          minRatings.get("x"), maxRatings.get("x"),
          minRatings.get("m"), maxRatings.get("m"),
          minRatings.get("a"), maxRatings.get("a"),
          minRatings.get("s"), maxRatings.get("s")));
    }

    int getMax(String s) {
      return maxRatings.get(s);
    }

    long posibilities() {
      return minRatings.entrySet().stream()
          .map(e -> (long) (maxRatings.get(e.getKey()) - e.getValue() + 1))
          .reduce((a, b) -> a * b)
          .get();
    }
  }

  enum Condition {
    LESS {
      @Override
      boolean check(int a, int b) {
        return a < b;
      }
    },
    GREATER {
      @Override
      boolean check(int a, int b) {
        return a > b;
      }
    };

    abstract boolean check(int a, int b);

    static Condition parse(String s) {
      return s.equals("<") ? LESS : GREATER;
    }
  }
}
