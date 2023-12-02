import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day01 {

    public static void main(String[] args) throws IOException {
        System.out.println(partA("input.txt"));
        System.out.println(partB("input.txt"));
    }

    public static Integer convertToNumber(String input) {
        final StringBuilder intermediate = new StringBuilder();
        IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .filter(Character::isDigit)
                .forEach(intermediate::append);
        String result = String.valueOf(intermediate.charAt(0)) +
                intermediate.charAt(intermediate.length() - 1);
        return Integer.valueOf(result);
    }

    public static Integer partA(String filename) throws IOException {
        return Files.lines(getNullSafePath(filename))
                .map(Day01::convertToNumber)
                .reduce(Integer::sum)
                .orElse(-1);
    }

    public static Integer partB(String filename) throws IOException {
        return Files.lines(getNullSafePath(filename))
                .map(Day01::extractNumber)
                .reduce(Integer::sum)
                .orElse(-1);
    }
    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day01.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }

    public static Integer extractNumber(String input) {
        var numberList= new String[]{"one","two","three","four","five","six", "seven","eight","nine"};
        int lowestIndex = Integer.MAX_VALUE;
        int lowestDigit = -1;
        int highestIndex = Integer.MIN_VALUE;
        int highestDigit = -1;

        for (var i =0; i< input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                if (i < lowestIndex) {
                    lowestIndex = i;
                    lowestDigit = Character.digit(c,10);
                }
                if (i> highestIndex) {
                    highestIndex = i;
                    highestDigit = Character.digit(c, 10);
                }
            }
        }


        for (int i=0; i < numberList.length; i++) {
            int index = input.indexOf(numberList[i]);
            if (index != -1 && index < lowestIndex) {
               lowestIndex = index;
               lowestDigit = i+1;
            }

            index = input.lastIndexOf(numberList[i]);
            if (index != -1 && index > highestIndex) {
                highestIndex = index;
                highestDigit = i+1;
            }
        }

        return Integer.valueOf("" + lowestDigit + highestDigit);
    }
}
