package com.guarajunior.guararp.util;

public class StringUtils {

    public static String toScreamingSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                if (Character.isUpperCase(c)) {
                    if (!result.isEmpty()) {
                        result.append('_');
                    }
                    result.append(Character.toUpperCase(c));
                } else {
                    result.append(Character.toUpperCase(c));
                }
            } else {
                result.append('_');
            }
        }

        return result.toString();
    }
}
