import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test {
    @ParameterizedTest
    @MethodSource("numberChallenges")
    void extractNumber(char[] line, int startingIndex, long result) {
        assertThat(Day03.extractNumber(line, startingIndex)).isEqualTo(result);
    }

    static Stream<Arguments> numberChallenges() {
        return Stream.of(
                Arguments.of("617*......".toCharArray(), 0, 617),
                Arguments.of("617*......".toCharArray(), 2, 617),
                Arguments.of("617*......".toCharArray(), 1, 617),
                Arguments.of("..617*......".toCharArray(), 2, 617),
                Arguments.of("..617*......".toCharArray(), 4, 617),
                Arguments.of("..617*......".toCharArray(), 3, 617)
        );
    }

    @Property
    boolean everyNonDigitOrDotIsSymbol(@ForAll("symbol") char c) {
        return Day03.isSymbol(c);
    }

    @Provide
    Arbitrary<Character> symbol() {
        var digitsAndDot = Set.of('0','1','2','3','4','5','6','7','8','9','.');
        return Arbitraries.chars().ascii().filter(not(digitsAndDot::contains));
    }


    @Test
    void partA() throws IOException {
        assertThat(Day03.partA("testInput.txt")).isEqualTo(4361L);
    }

    @Test
    void partB() throws IOException {
        assertThat(Day03.partB("testInput.txt")).isEqualTo(467835L);
    }
}
