package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Trebuchet {

    public static void main(String[] args) throws FileNotFoundException {
        File calibrationFile = new File("src/main/resources/calibration");
        System.out.println("Sum of calibration values: " +  calculateCalibrationSum(calibrationFile));
    }

    public static String wordsToNumber(String withWords) {
        Map<String, Integer> wordToNumber = Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9
        );

        for (Map.Entry<String, Integer> entry : wordToNumber.entrySet()) {
            String word = entry.getKey();
            int number = entry.getValue();
            String replacement = word + number + word;
            withWords = withWords.replace(word, replacement);
        }
        return withWords;
    }

    public static int calculateCalibrationSum(File input) throws FileNotFoundException {
        int sum = 0;
        Scanner fileReader = new Scanner(input);

        while (fileReader.hasNextLine()) {
            String lineWithText = fileReader.nextLine();
            String onlyDigits = wordsToNumber(lineWithText).replaceAll("[^0-9\\.]", "");
            sum += Character.getNumericValue(onlyDigits.charAt(0)) * 10 + Character.getNumericValue(onlyDigits.charAt(onlyDigits.length() - 1));
        }
        fileReader.close();
        return sum;
    }
}