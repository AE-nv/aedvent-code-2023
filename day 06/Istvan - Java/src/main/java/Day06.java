import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.lang.Character.isDigit;

public class Day06 {
    public static void main(String[] args) throws IOException {
        String s = Files.readString(getNullSafePath("input.txt"));
        System.out.println("Part A: " + partA(s));
        System.out.println("Part B: " + partB(s));
    }

    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day06.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }

    public static long determineMinSpeed(long duration, long recordDistance) {
        return findMinSpeed(1, duration-1, duration, recordDistance);
    }

    public static long findMinSpeed(long minHoldTime, long maxHoldTime,long raceDuration, long recordDistance) {
        long diff = maxHoldTime - minHoldTime;
        long testValue = minHoldTime + diff /2;
        long distance = testValue * (raceDuration - testValue);

        if (distance > recordDistance)
            return findMinSpeed(minHoldTime, testValue, raceDuration, recordDistance);
        else {
           return  diff ==1L? maxHoldTime: findMinSpeed(testValue, maxHoldTime, raceDuration, recordDistance);
        }
    }

    public static long determineMaxSpeed(long duration, long recordDistance) {
        return findMaxSpeed(1, duration-1, duration, recordDistance);
    }

    public static long findMaxSpeed(long minHoldTime, long maxHoldTime,long raceDuration, long recordDistance) {
        long diff = maxHoldTime - minHoldTime;
        long testValue = maxHoldTime - diff /2;
        long distance = testValue * (raceDuration - testValue);

        if (distance > recordDistance)
            return  findMaxSpeed(testValue, maxHoldTime, raceDuration, recordDistance);
        else {
            return diff == 1L ? minHoldTime:findMaxSpeed(minHoldTime, testValue, raceDuration, recordDistance);
        }
    }

    public static long determineSpeedOptions(long duration, long recordDistance) {
        return determineMaxSpeed(duration, recordDistance) - determineMinSpeed(duration, recordDistance)+1;
    }

    public static long partA(String input) {
        var lines = input.split("\\n");
        var times = lines[0].split(":")[1].trim().split(" +");
        var distances = lines[1].split(":")[1].trim().split(" +");
        return IntStream.range(0, times.length)
                .boxed()
                .map(i -> determineSpeedOptions(Integer.parseInt(times[i]),Integer.parseInt(distances[i])))
                .reduce(1L, (aLong, aLong2) -> aLong*aLong2);
    }

    public static long partB(String input) {
        var lines = input.split("\\r\\n");
        long duration = Long.parseLong(lines[0].split(":")[1].replaceAll(" ", ""));
        long distance = Long.parseLong(lines[1].split(":")[1].replaceAll(" ", ""));
        return determineSpeedOptions(duration, distance);

    }
}