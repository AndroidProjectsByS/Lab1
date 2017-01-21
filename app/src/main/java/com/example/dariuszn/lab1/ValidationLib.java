package com.example.dariuszn.lab1;

/**
 * Created by DariuszN on 18.01.2017.
 */

public class ValidationLib {

    public static boolean isCorrectText(String text) {
        boolean isCorrect = true;

        String pattern = "^[\\p{L}]+$";
        isCorrect = text.matches(pattern);

        return isCorrect;
    }

    public static boolean isCorrectNumber(String text) {
        boolean isCorrect = true;

        String pattern = "^[0-9]+$";
        isCorrect = text.matches(pattern);

        return isCorrect;
    }


    public static boolean isCorrectNumberOfRate(String text) {
        boolean isCorrect = ValidationLib.isCorrectNumber(text);
        if (isCorrect) {
            int number = Integer.parseInt(text);
            if (number < 5 || number > 15) {
                isCorrect = false;
            }
        }

        return isCorrect;

    }
}
