import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {

    @ParameterizedTest
    @MethodSource("providePartATestInputLineByLine")
    void convertToNumber(String input, Integer expected) {
        assertThat(Day01.convertToNumber(input)).isEqualTo(expected);
    }


    @ParameterizedTest
    @MethodSource("providePartBTestInputLineByLine")
    void extractNumber(String input, Integer expected) {
        assertThat(Day01.extractNumber(input)).isEqualTo(expected);
    }
    @Test
    void partA() throws IOException {
        String filename = "testPartA.txt";
        assertThat(Day01.partA(filename)).isEqualTo(142);
    }
    private static Stream<Arguments> providePartATestInputLineByLine() {
        return Stream.of(
                Arguments.of("1abc2", 12),
                Arguments.of("pqr3stu8vwx",38),
                Arguments.of("a1b2c3d4e5f", 15),
                Arguments.of("treb7uchet",77)
        );
    }


    private static Stream<Arguments> providePartBTestInputLineByLine() {
        return Stream.of(
                Arguments.of("two1nine", 29),
                Arguments.of("eightwothree",83),
                Arguments.of("abcone2threexyz", 13),
                Arguments.of("xtwone3four",24),
                Arguments.of("4nineeightseven2",42),
                Arguments.of("zoneight234",14),
                Arguments.of("7pqrstsixteen",76)
        );
    }
}
