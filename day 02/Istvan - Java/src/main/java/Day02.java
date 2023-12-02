import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class Day02 {

    public static void main(String[] args) throws IOException {
        System.out.println("Part A: " + partA("input.txt"));
        System.out.println("Part B: " + partB("input.txt"));
    }

    public static boolean partAPredicate(Game game) {
        final int maxBlue = 14, maxRed = 12, maxGreen = 13;
        return game.draws.stream()
                .filter(Predicate.not(
                        draw -> draw.blue <= maxBlue
                                && draw.red <= maxRed
                                && draw.green <= maxGreen))
                .findFirst()
                .isEmpty();
    }

    public static Draw parseDraw(String input) {
        var parts = input.split(",");
        int red = 0, blue = 0, green = 0;
        for (var part : parts) {
            var split = part.trim().split(" ");
            switch (split[1]) {
                case "blue":
                    blue = Integer.parseInt(split[0]);
                    break;
                case "red":
                    red = Integer.parseInt(split[0]);
                    break;
                case "green":
                    green = Integer.parseInt(split[0]);
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected color: " + split[1]);
            }
        }
        return new Draw(blue, red, green);
    }

    public static Game parseGame(String input) {
        var idAndDraws = input.split(":");
        var game = new Game(idAndDraws[0].split(" ")[1]);
        Arrays.stream(idAndDraws[1].split(";"))
                .map(Day02::parseDraw)
                .forEach(game::addDraw);
        return game;
    }

    public static Integer partA(String file) throws IOException {
        return Files.lines(getNullSafePath(file))
                .map(Day02::parseGame)
                .filter(Day02::partAPredicate)
                .map(Game::id)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .orElseThrow();
    }

    public static long partB(String file) throws IOException {
        return Files.lines(getNullSafePath(file))
                .map(Day02::parseGame)
                .map(Day02::determineMinCubeSet)
                .map(MinCubeSet::getPower)
                .reduce(0L, Long::sum);
    }
    public static Path getNullSafePath(String filename) {
        try {
            return Paths.get(
                    Objects.requireNonNull(
                            Day02.class.getClassLoader().getResource(filename)).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not find file " + filename, e);
        }
    }

    public static MinCubeSet determineMinCubeSet(Game game) {
        return game.draws.stream()
                .map(d -> new MinCubeSet(d.blue, d.red, d.green))
                .reduce(MinCubeSet.IDENTITY,
                        (j,k) -> new MinCubeSet(
                                Long.max(j.blue, k.blue), Long.max(j.red, k.red),Long.max(j.green, k.green)));

    }


    public record Draw(int blue, int red, int green) {
    }

    public static class Game {
        private String id;
        private List<Draw> draws;

        public Game(String id) {
            this.id = id;
            this.draws = new LinkedList<>();
        }

        public String id() {
            return id;
        }

        public List<Draw> getDraws() {
            return Collections.unmodifiableList(this.draws);
        }

        public void addDraw(Draw draw) {
            this.draws.add(draw);
        }
    }

    public record MinCubeSet(long blue, long red, long green) {

        public static final MinCubeSet IDENTITY = new MinCubeSet(0,0,0);

        long getPower() {
            return blue * red * green;
        }
    }

}
