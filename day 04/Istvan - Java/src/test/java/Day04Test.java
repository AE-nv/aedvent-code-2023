import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day04Test {

    @ParameterizedTest
    @MethodSource("getLinesFromExample")
    void readLine(String line, String id, List<Integer> winningNumbers, Set<Integer> numbers, long score) {
        var card = Day04.parseLine(line);
        assertThat(card).isNotNull();
        assertThat(card.id()).isEqualTo(id);
        assertThat(card.winningNumbers()).containsExactly(winningNumbers.toArray(new Integer[0]));
        assertThat(card.numbers()).containsAll(numbers);
        assertThat(card.score()).isEqualTo(score);
    }

    static Stream<Arguments> getLinesFromExample() {
        return Stream.of(
                Arguments.of("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53", "1", List.of(41, 48, 83, 86, 17), Set.of(83, 86, 6, 31, 17, 9, 48, 53), 8),
                Arguments.of("Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19", "2", List.of(13, 32, 20, 16, 61), Set.of(61, 30, 68, 82, 17, 32, 24, 19), 2),
                Arguments.of("Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1", "3", List.of(1, 21, 53, 59, 44), Set.of(69, 82, 63, 72, 16, 21, 14, 1), 2),
                Arguments.of("Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83", "4", List.of(41, 92, 73, 84, 69), Set.of(59, 84, 76, 51, 58, 5, 54, 83), 1),
                Arguments.of("Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36", "5", List.of(87, 83, 26, 28, 32), Set.of(88, 30, 70, 12, 93, 22, 82, 36), 0),
                Arguments.of("Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11", "6", List.of(31, 18, 13, 56, 72), Set.of(74, 77, 10, 23, 35, 67, 36, 11), 0)
        );
    }

    @Test
    void partA() throws IOException {
        assertThat(Day04.partA("testInput.txt")).isEqualTo(13L);
    }

    @Test
    void partB() throws IOException {
        assertThat(Day04.partB("testInput.txt")).isEqualTo(30L);
    }

}
