package com.ku.quizzical.common.helper;

/**
 * Helper class for Thread Operations
 */
public class ThreadHelper {
    /**
     * Gets a String representing the time from a specific epoch milliseconds usable as a file name
     * and sortable by alphanumeric comparison
     */
    public static void sleep(long numberOfMilliseconds) {
        try {
            Thread.sleep(numberOfMilliseconds);
        } catch (Exception e) {
            // Do Nothing
        }
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ThreadHelper() {
        super();
    }
}
