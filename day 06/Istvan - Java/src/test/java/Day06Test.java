import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day06Test {

    @ParameterizedTest
    @MethodSource("getRaceTimeAndRecordDistanceAndMinHoldTime")
    void determineMinSpeed(int duration, int recordDistance, int minHoldTime) {
        assertThat(Day06.determineMinSpeed(duration, recordDistance)).isEqualTo(minHoldTime);
    }

    static Stream<Arguments> getRaceTimeAndRecordDistanceAndMinHoldTime (){
        return Stream.of(
               Arguments.of(7,9,2) ,
                Arguments.of(15,40,4),
                Arguments.of(30,200,11)
        );
    }

    @ParameterizedTest
    @MethodSource("getRaceTimeAndRecordDistanceAndMaxHoldTime")
    void determineMaxSpeed(int duration, int recordDistance, int maxHoldTime) {
        assertThat(Day06.determineMaxSpeed(duration, recordDistance)).isEqualTo(maxHoldTime);
    }

    static Stream<Arguments> getRaceTimeAndRecordDistanceAndMaxHoldTime(){
        return Stream.of(
                Arguments.of(7,9,5) ,
                Arguments.of(15,40,11),
                Arguments.of(30,200,19)
        );
    }

    @ParameterizedTest
    @MethodSource("getRaceTimeAndRecordDistanceAndNumberOfOptions")
    void determineSpeedOptions(int duration, int recordDistance, int numberOfOptions) {
        assertThat(Day06.determineSpeedOptions(duration, recordDistance)).isEqualTo(numberOfOptions);
    }

    static Stream<Arguments> getRaceTimeAndRecordDistanceAndNumberOfOptions( ){
        return Stream.of(
                Arguments.of(7,9,4) ,
                Arguments.of(15,40,8),
                Arguments.of(30,200,9)
        );
    }

    @Test
    void partA() {
        var input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        assertThat(Day06.partA(input)).isEqualTo(288);
    }

    @Test
    void partB() {

        var input = """
                Time:      7  15   30
                Distance:  9  40  200""";
        assertThat(Day06.partB(input)).isEqualTo(71503);
    }
}
