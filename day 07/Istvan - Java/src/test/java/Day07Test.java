import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day07Test {


    @ParameterizedTest
    @MethodSource("getHandsAndTypes")
    void createHand(String handAsString, Day07.Type type) {
        var hand = Day07.createHand(handAsString, "123", false);
        assertThat(hand).isNotNull();
        assertThat(hand.type()).isNotNull();
        assertThat(hand.type()).isEqualTo(type);
    }

    static Stream<Arguments> getHandsAndTypes() {
        return Stream.of(
          Arguments.of("12345", Day07.Type.HIGH_CARD),
                Arguments.of("AAAAA", Day07.Type.FIVE_OF_A_KIND),
                Arguments.of("B2B2B", Day07.Type.FULL_HOUSE),
                Arguments.of("B2BBB", Day07.Type.FOUR_OF_A_KIND),
                Arguments.of("B2122", Day07.Type.THREE_OF_A_KIND),
                Arguments.of("12321", Day07.Type.TWO_PAIR),
                Arguments.of("KK677", Day07.Type.TWO_PAIR),
                Arguments.of("KTJJT", Day07.Type.TWO_PAIR),
                Arguments.of("12Q3Q", Day07.Type.ONE_PAIR)
        );
    }

    @Test
    void compareHands() {
        var hand1 = new Day07.Hand("KK677",28L);
        var hand2 = new Day07.Hand("KTJJT", 220L);
        assertThat(hand1.compareTo(hand2)).isPositive();
    }

    @Test
    void partA() throws IOException {
        assertThat(Day07.partA("testInput.txt")).isEqualTo(6440L);
    }

    @Test
    void partB() throws IOException {
        assertThat(Day07.partB("testInput.txt")).isEqualTo(5905L);
    }
}
