import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Handler;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;

public class Day07 {
    public static void main(String[] args) throws IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day07.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }

    public static IHand createHand(String input, String bet, boolean jokerSupport) {
        return jokerSupport?new JokerHand(input,Long.parseLong(bet)):new Hand(input, Long.parseLong(bet));
    }

    public static long partA(String file) throws IOException {
        return determineScore(file, false);
    }

    public static long partB(String file) throws IOException {
        return determineScore(file, true);
    }
    private static Long determineScore(String file, boolean jokerSupport) throws IOException {
        Map<Type, List<IHand>> hands = Files.lines(getNullSafePath(file))
                .map(l -> l.trim().split(" +"))
                .map(parts -> createHand(parts[0],parts[1], jokerSupport))
                .collect(Collectors.groupingBy(IHand::type, Collectors.toList()));

        AtomicLong counter = new AtomicLong(1);
        return Arrays.stream(Type.values())
                .sorted(Comparator.comparingInt(t -> t.strength))
                .flatMap(t -> hands.getOrDefault(t, Collections.emptyList()).stream())
                .sorted()
                .map(IHand::bet)
                .map(bet -> counter.getAndIncrement() * bet)
                .reduce(0L, Long::sum);
    }

    public interface IHand {
        Type type();

        long bet();
    }
    public record Hand(String cards, long bet, Type type) implements IHand, Comparable<Hand>{
        public Hand(String cards, long bet) {
            this(cards, bet, determineType(cards));
        }

        private static Type determineType(String cards) {
            Map<Character, Long> mapped = cards.chars()
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.groupingBy(c-> c, Collectors.counting()));
            return switch (mapped.size()){
                case 1 -> Type.FIVE_OF_A_KIND;
                case 2 -> mapped.containsValue(4L)?Type.FOUR_OF_A_KIND:Type.FULL_HOUSE;
                case 3 -> mapped.containsValue(3L)?Type.THREE_OF_A_KIND:Type.TWO_PAIR;
                case 4 -> Type.ONE_PAIR;
                case 5 -> Type.HIGH_CARD;
                default -> throw new IllegalArgumentException("Unexpected Size");
            };
        }

        @Override
        public int compareTo(Hand other) {
            if (type() != other.type())
                return Integer.compare(type().strength, other.type().strength);

            String cardsByStrengthsAsc = "23456789TJQKA";
            int i = 0;
            char c1 = cards.charAt(i);
            char c2 = other.cards.charAt(i);
            while (i<4 && c1 == c2) {
                i++;
                c1= cards.charAt(i);
                c2= other.cards.charAt(i);
            }
            return i==5?0:Integer.compare(cardsByStrengthsAsc.indexOf(c1), cardsByStrengthsAsc.indexOf(c2));
        }
    }


    public record JokerHand(String cards, long bet, Type type) implements IHand, Comparable<JokerHand>{
        public JokerHand(String cards, long bet) {
            this(cards, bet, determineType(cards));
        }

        private static Type determineType(String cards) {
            Map<Character, Long> mapped = cards.chars()
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.groupingBy(c-> c, Collectors.counting()));
            return switch (mapped.size()){
                case 1 -> Type.FIVE_OF_A_KIND;
                case 2 -> mapped.containsKey('J')? Type.FIVE_OF_A_KIND:mapped.containsValue(4L)?Type.FOUR_OF_A_KIND:Type.FULL_HOUSE;
                case 3 -> {
                    if (mapped.containsValue(3L))
                        yield mapped.containsKey('J')?Type.FOUR_OF_A_KIND:Type.THREE_OF_A_KIND;
                    var numberOfJokers = mapped.get('J');
                    if (numberOfJokers != null)
                        yield numberOfJokers == 1L?Type.FULL_HOUSE: Type.FOUR_OF_A_KIND;
                    yield Type.TWO_PAIR;
                }
                case 4 -> mapped.containsKey('J')?Type.THREE_OF_A_KIND:Type.ONE_PAIR;
                case 5 -> mapped.containsKey('J')?Type.ONE_PAIR:Type.HIGH_CARD;
                default -> throw new IllegalArgumentException("Unexpected Size");
            };
        }

        @Override
        public int compareTo(JokerHand other) {
            if (type() != other.type())
                return Integer.compare(type().strength, other.type().strength);

            String cardsByStrengthsAsc = "J23456789TQKA";
            int i = 0;
            char c1 = cards.charAt(i);
            char c2 = other.cards.charAt(i);
            while (i<4 && c1 == c2) {
                i++;
                c1= cards.charAt(i);
                c2= other.cards.charAt(i);
            }
            return i==5?0:Integer.compare(cardsByStrengthsAsc.indexOf(c1), cardsByStrengthsAsc.indexOf(c2));
        }
    }
    public static enum Type {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        final int strength;

        Type(int strength) {
            this.strength = strength;
        }
    }
}