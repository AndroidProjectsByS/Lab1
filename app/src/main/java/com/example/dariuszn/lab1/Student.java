package com.example.dariuszn.lab1;

/**
 * Created by DariuszN on 18.01.2017.
 */

public class Student {
    public static String firstName = "";
    public static String lastName = "";
    public static int numberOfRates = 0;
    public static float averageRates = 0;
    public static boolean hasTwoRate = false;

    public static void setNamesAndNumberOfRates(String fName, String lName, int numberOfRates) {
        Student.firstName = fName;
        Student.lastName = lName;
        Student.numberOfRates = numberOfRates;
    }

    public static void killAllValues() {
        firstName = "";
        lastName = "";
        numberOfRates = 0;
        averageRates = 0;
        hasTwoRate = false;
    }
}

