import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Character.isDigit;

public class Day03 {
    public static void main(String[] args) throws IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }
    public static long extractNumber(char[] line, int startingIndex) {
        StringBuilder builder = new StringBuilder(3);
        for (var i = startingIndex; i < line.length && isDigit(line[i]); i++) {
            builder.append(line[i]);
        }
        for (var i = startingIndex-1; i >= 0 && isDigit(line[i]); i--) {
            builder.insert(0,line[i]);
        }
        return Long.parseLong(builder.toString());
    }

    public static boolean isSymbol(char c) {
        return !(isDigit(c) || c == '.');
    }

    public static long partA(String file) throws IOException {
        List<char[]> lines = Files.lines(getNullSafePath(file))
                .map(String::toCharArray)
                .toList();
        long sum =0L;
        for (var row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for(var col =0; col < line.length; col++ ) {
                if (isSymbol(line[col])) {
                    if (col > 0 && isDigit(line[col-1])) sum += extractNumber(line, col-1);
                    if (col < line.length-1 && isDigit(line[col+1])) sum += extractNumber(line, col+1);
                    if (row > 0 ) {
                        var prevLine = lines.get(row-1);
                        var firstCandidate =-1L;
                        if (col > 0 && isDigit(prevLine[col-1])) firstCandidate = extractNumber(prevLine, col-1);
                        var secondCandidate = -1L;
                        if (col < prevLine.length-1 && isDigit(prevLine[col+1])) secondCandidate= extractNumber(prevLine, col+1);
                        var thirdCandidate = -1L;
                        if (isDigit(prevLine[col])) thirdCandidate = extractNumber(prevLine, col);

                        if (firstCandidate > -1) sum+=firstCandidate;
                        if (secondCandidate > -1 && firstCandidate != secondCandidate) sum += secondCandidate;
                        if (thirdCandidate > -1 && firstCandidate != thirdCandidate && secondCandidate != thirdCandidate) sum+= thirdCandidate;
                    }
                    if (row < lines.size()-1) {
                        var nextLine = lines.get(row+1);
                        var firstCandidate =-1L;
                        if (col > 0 && isDigit(nextLine[col-1])) firstCandidate = extractNumber(nextLine, col-1);
                        var secondCandidate = -1L;
                        if (col < nextLine.length-1 && isDigit(nextLine[col+1])) secondCandidate= extractNumber(nextLine, col+1);
                        var thirdCandidate = -1L;
                        if (isDigit(nextLine[col])) thirdCandidate = extractNumber(nextLine, col);

                        if (firstCandidate > -1) sum+=firstCandidate;
                        if (secondCandidate > -1 && firstCandidate != secondCandidate) sum += secondCandidate;
                        if (thirdCandidate > -1 && firstCandidate != thirdCandidate && secondCandidate != thirdCandidate) sum+= thirdCandidate;

                    }
                }
            }
        }
        return sum;
    }

    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day03.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }

    public static long partB(String file) throws IOException {
        List<char[]> lines = Files.lines(getNullSafePath(file))
                .map(String::toCharArray)
                .toList();
        long sum =0L;
        for (var row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for(var col =0; col < line.length; col++ ) {
                if ('*' == line[col]) {
                    List<Long> numbersAround = new ArrayList<>(6);
                    if (col > 0 && isDigit(line[col-1])) numbersAround.add(extractNumber(line, col-1));
                    if (col < line.length-1 && isDigit(line[col+1])) numbersAround.add(extractNumber(line, col+1));
                    if (row > 0 ) {
                        var prevLine = lines.get(row-1);
                        var firstCandidate =-1L;
                        if (col > 0 && isDigit(prevLine[col-1])) firstCandidate = extractNumber(prevLine, col-1);
                        var secondCandidate = -1L;
                        if (col < prevLine.length-1 && isDigit(prevLine[col+1])) secondCandidate= extractNumber(prevLine, col+1);
                        var thirdCandidate = -1L;
                        if (isDigit(prevLine[col])) thirdCandidate = extractNumber(prevLine, col);

                        if (firstCandidate > -1) numbersAround.add(firstCandidate);
                        if (secondCandidate > -1 && firstCandidate != secondCandidate) numbersAround.add(secondCandidate);
                        if (thirdCandidate > -1 && firstCandidate != thirdCandidate && secondCandidate != thirdCandidate) numbersAround.add(thirdCandidate);
                    }
                    if (row < lines.size()-1) {
                        var nextLine = lines.get(row+1);
                        var firstCandidate =-1L;
                        if (col > 0 && isDigit(nextLine[col-1])) firstCandidate = extractNumber(nextLine, col-1);
                        var secondCandidate = -1L;
                        if (col < nextLine.length-1 && isDigit(nextLine[col+1])) secondCandidate= extractNumber(nextLine, col+1);
                        var thirdCandidate = -1L;
                        if (isDigit(nextLine[col])) thirdCandidate = extractNumber(nextLine, col);

                        if (firstCandidate > -1) numbersAround.add(firstCandidate);
                        if (secondCandidate > -1 && firstCandidate != secondCandidate) numbersAround.add(secondCandidate);
                        if (thirdCandidate > -1 && firstCandidate != thirdCandidate && secondCandidate != thirdCandidate) numbersAround.add(thirdCandidate);
                    }
                    if (numbersAround.size() == 2) sum += numbersAround.get(0)* numbersAround.get(1);
                }
            }
        }
        return sum;
    }
}
