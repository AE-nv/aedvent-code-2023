package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CubeConundrum2 {
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

            int redCount = 0, greenCount = 0, blueCount = 0;

            for (String gameRound : gameInput) {

                String[] colorValue = gameRound.split("[,\\s]+");

                for (int i = 0; i < colorValue.length; i += 2) {
                    int count = Integer.parseInt(colorValue[i]);
                    switch (colorValue[i + 1].trim()) {
                        case "red":
                            if(redCount < count){
                                redCount = count;
                            }
                            break;
                        case "green":
                            if(greenCount < count) {
                                greenCount = count;
                            }
                            break;
                        case "blue":
                            if(blueCount < count) {
                                blueCount = count;
                            }
                            break;
                    }
                }
            }
                sumOfGames += redCount * greenCount * blueCount;
        }
        return sumOfGames;
    }

    }