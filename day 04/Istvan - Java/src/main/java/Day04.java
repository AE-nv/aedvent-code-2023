import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;

public class Day04 {
    public static void main(String[] args) throws IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    public static Card parseLine(String line) {
        var parts = line.split("(:|\\|)");
        var winningNumbers = Arrays.stream(parts[1].trim().split(" +")).map(Integer::valueOf).toList();
        var numbers = Arrays.stream(parts[2].trim().split(" +")).map(Integer::valueOf).collect(Collectors.toSet());
        return new Card(parts[0].split(" +")[1],winningNumbers, numbers);
    }

    public static long partA(String file) throws IOException {
        return Files.lines(getNullSafePath(file))
                .map(Day04::parseLine)
                .map(Card::score)
                .reduce(0L, Long::sum);
    }

    public static long partB(String file) throws IOException {
        var origDeck = Files.lines(getNullSafePath(file))
                .map(Day04::parseLine)
                .toList();
        long count = 0L;
        var addedCards = origDeck;

        while (!addedCards.isEmpty()) {
            count += addedCards.size();
            addedCards = processDeck(addedCards, origDeck);
        }

        return count;
    }

    public static List<Card> processDeck(List<Card> toProcess, List<Card> origDeck) {
        return toProcess.stream()
                .flatMap(c -> origDeck.subList(Integer.parseInt(c.id()), Integer.parseInt(c.id())+(int)c.matches).stream())
                .toList();
    }

    public static Stream<Card> addWinnings(Card c, List<Card> deck) {
        var currCardId = Integer.parseInt(c.id());
        var newCards =  deck.subList(currCardId,currCardId+(int)c.matches);
        return Stream.concat(Stream.of(c), newCards.stream());
    }
    public record Card(String id, List<Integer> winningNumbers, Set<Integer> numbers, long matches) {

        public Card(String id, List<Integer> winningNumbers, Set<Integer> numbers) {
            this(id, winningNumbers, numbers, winningNumbers.stream().filter(numbers::contains).count());
        }
        public long score() {
            return (long) Math.pow(2, matches-1);
        }
    }

    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day04.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }
}
