import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Round(
    int red,
    int green,
    int blue) {

  public boolean isPossible(int r, int g, int b) {
    return red <= r && green <= g && blue <= b;
  }

  public void print() {
    System.out.println(String.format("  r:%o, g:%o, b:%o", red, green, blue));
  }

  public static Round parse(String line) {
    int red = 0;
    int green = 0;
    int blue = 0;
    for (String element : line.split(",")) {
      if (element.contains("red")) {
        red = Integer.parseInt(element.replace("red", "").strip());
      }
      if (element.contains("green")) {
        green = Integer.parseInt(element.replace("green", "").strip());
      }
      if (element.contains("blue")) {
        blue = Integer.parseInt(element.replace("blue", "").strip());
      }
    }
    return new Round(red, green, blue);
  }

  public static Round parse2(String line) {
    Map<String, Integer> rgb = Stream.of(line.split(","))
        .map(part -> part.strip())
        .map(x -> x.split(" "))
        .collect(Collectors.toMap(
            x -> x[1],
            x -> Integer.parseInt(x[0])));

    return new Round(
        rgb.getOrDefault("red", 0),
        rgb.getOrDefault("green", 0),
        rgb.getOrDefault("blue", 0));
  }

}
