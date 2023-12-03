package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class CubeConundrum1 {
    public static void main(String[] args) throws FileNotFoundException {
        File calibrationFile = new File("src/main/resources/game");

        System.out.println("result of game: " + calculateGame(calibrationFile));
    }

    public static int calculateGame(File input) throws FileNotFoundException {
        int sumOfGames = 0;
        Scanner fileReader = new Scanner(input);

        while (fileReader.hasNextLine()) {
            String[] game = fileReader.nextLine().split(":\\s");
            String[] gameInput = game[1].split(";\\s");

            boolean possibleGame = true;

            for (String gameRound : gameInput) {
                int redCount = 0, greenCount = 0, blueCount = 0;

                String[] colorValue = gameRound.split("[,\\s]+");

                for (int i = 0; i < colorValue.length; i += 2) {
                    int count = Integer.parseInt(colorValue[i]);
                    switch (colorValue[i + 1].trim()) {
                        case "red":
                            redCount += count;
                            break;
                        case "green":
                            greenCount += count;
                            break;
                        case "blue":
                            blueCount += count;
                            break;
                    }
                }

                if (redCount > 12 || greenCount > 13 || blueCount > 14) {
                    possibleGame = false;
                    break;
                }
            }

            if (possibleGame) {
                String gameId = game[0].split(" ")[1];
                sumOfGames += Integer.parseInt(gameId);
            }
        }
        return sumOfGames;
    }

    }