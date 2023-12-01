import java.util.Map;

public class Test {
  public static void main(String[] args) {
    System.out.println(part1());
    System.out.println(part2());
  }

  public static Integer part1() {
    return Data.getData()
        .lines()
        .map(x-> getFirstAndLastNumber(x))
        .reduce((a, b) ->  a+b).get();     
  }

  public static int getFirstAndLastNumber(String line) {
    String numbers = line.replace("[A-Za-z]", "");
    String number = "" + numbers.charAt(0) + numbers.charAt(numbers.length()-1);
    return Integer.parseInt(number);
  }

  public static Integer part2() {
    return Data.getData()
    .lines()
    .map(x -> lettersToNumbers(x))
    .map(x ->getFirstAndLastNumber(x))
    .reduce((a,b) -> a+b)
    .get();
  }

  public static String lettersToNumbers(String line) {
    for (Map.Entry<String,String> entry : map.entrySet()) {
        line = line.replace(entry.getKey(), entry.getKey() + entry.getValue() + entry.getKey());
      }
      return line;
  }

  public static Map<String, String> map = Map
      .of("one", "1",
          "two", "2",
          "three","3",
          "four", "4",
          "five", "5",
          "six", "6",
          "seven", "7", 
          "eight", "8", 
          "nine", "9");
}
