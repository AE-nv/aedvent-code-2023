import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02Test {

    @ParameterizedTest
    @MethodSource("listGamesAndMinCubes")
    void mapToMinCubeSet(String input, long minBlue, long minRed, long minGreen, long power) {
        var game = Day02.parseGame(input);
        var minCubeSet = Day02.determineMinCubeSet(game);
        assertThat(minCubeSet).isNotNull();
        assertThat(minCubeSet.blue()).isEqualTo(minBlue);
        assertThat(minCubeSet.red()).isEqualTo(minRed);
        assertThat(minCubeSet.green()).isEqualTo(minGreen);
        assertThat(minCubeSet.getPower()).isEqualTo(power);
    }

    public static Stream<Arguments> listGamesAndMinCubes() {
        return Stream.of(
                Arguments.of("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", 6,4,2,48),
                Arguments.of("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", 4,1,3,12),
                Arguments.of("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",6,20,13,1560),
                Arguments.of("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", 15,14,3,630),
                Arguments.of("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", 2,6,3,36)
        );
    }

    @Test
    void partA() throws IOException {
        assertThat(Day02.partA("testInput.txt")).isEqualTo(8);
    }

    @Test
    void partB() throws IOException {
        assertThat(Day02.partB("testInput.txt")).isEqualTo(2286);
    }
    public static Stream<Arguments> listGames() {
        return Stream.of(
                Arguments.of("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", "1", 3),
                Arguments.of("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", "2", 3),
                Arguments.of("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", "3", 3),
                Arguments.of("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", "4", 3),
                Arguments.of("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", "5", 2)
        );
    }

    @Test
    void evaluateValidGame() {
        var game = new Day02.Game("3");
        game.addDraw(new Day02.Draw(3,4,0));
        game.addDraw(new Day02.Draw(6,1,2));
        game.addDraw(new Day02.Draw(0,0,2));
        assertThat(Day02.partAPredicate(game)).isTrue();
    }

    @Test
    void evaluateInvalidGame() {
        var game = new Day02.Game("4");
        game.addDraw(new Day02.Draw(6,3,1));
        game.addDraw(new Day02.Draw(0,6,3));
        game.addDraw(new Day02.Draw(15,14,3));
    }

    @ParameterizedTest
    @MethodSource("listDraws")
    void parseDraw(String input, int expectedBlue, int expectedRed, int expectedGreen) {
        var draw = Day02.parseDraw(input);
        assertThat(draw.blue()).isEqualTo(expectedBlue);
        assertThat(draw.red()).isEqualTo(expectedRed);
        assertThat(draw.green()).isEqualTo(expectedGreen);
    }

    @ParameterizedTest
    @MethodSource("listGames")
    void parseGame(String input, String expectedId, int expectecNumberOfDraws) {
        var game = Day02.parseGame(input);
        assertThat(game.id()).isEqualTo(expectedId);
        assertThat(game.getDraws()).hasSize(expectecNumberOfDraws);
    }

    static Stream<Arguments> listDraws() {
        return Stream.of(
          Arguments.of("3 blue, 4 red", 3,4,0),
                Arguments.of("1 red, 2 green, 6 blue",6,1,2),
                Arguments.of("2 green",0,0,2),
                Arguments.of("1 blue, 2 green",1,0,2),
                Arguments.of("3 green, 4 blue, 1 red",4,1,3),
                Arguments.of("1 green, 1 blue",1,0,1),
                Arguments.of("8 green, 6 blue, 20 red",6,20,8),
                Arguments.of("5 blue, 4 red, 13 green",5,4,13),
                Arguments.of(" 5 green, 1 red",0,1,5),
                Arguments.of("1 green, 3 red, 6 blue",6,3,1),
                Arguments.of("3 green, 6 red",0,6,3),
                Arguments.of("3 green, 15 blue, 14 red",15, 14,3),
                Arguments.of("6 red, 1 blue, 3 green",1,6,3),
                Arguments.of("2 blue, 1 red, 2 green",2,1,2)
        );
    }
}
