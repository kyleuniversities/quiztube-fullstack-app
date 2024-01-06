package com.ku.quizzical.common.helper;

/**
 * Helper class for Printing Operations
 */
public class PrintHelper {
    /**
     * Prints an empty line
     */
    public static void printEmptyLine() {
        PrintHelper.printLine("");
    }

    /**
     * Prints a line
     */
    public static void printEntry(String key, String value) {
        PrintHelper.printLine(key + ": " + value);
    }

    /**
     * Prints a line
     */
    public static void printLine(String message) {
        System.out.println(message);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private PrintHelper() {
        super();
    }
}
