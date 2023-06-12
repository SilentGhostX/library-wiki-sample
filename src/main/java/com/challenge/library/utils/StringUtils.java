package com.challenge.library.utils;

public class StringUtils {

    public static String[] split(String input, String delimiter) {
        // Validate and check if the input is not Empty
        if (input == null || input.length() == 0) {
            return new String[]{};
        }
        // Remove whitespaces
        input = input.replaceAll("\\s+", "");
        // Split by delimiter and return the string array
        return input.split(delimiter);
    }

}
