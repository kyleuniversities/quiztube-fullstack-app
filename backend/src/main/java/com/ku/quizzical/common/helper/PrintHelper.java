package com.ku.quizzical.common.helper;

import java.util.List;
import java.util.Map;
import com.ku.quizzical.common.helper.string.StringHelper;

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
    public static void printEntry(String key, Object value) {
        PrintHelper.printLine(key + ": " + value);
    }

    /**
     * Prints a formatted line
     */
    public static void printFormattedLine(String message, Object... args) {
        System.out.println(StringHelper.format(message, args));
    }

    /**
     * Prints a line
     */
    public static void printLine(String message) {
        System.out.println(message);
    }

    /**
     * Prints a List
     */
    public static <T> void printList(String title, List<T> list) {
        PrintHelper.printFormattedLine("<<%s>>", title);
        ListHelper.forEach(list, (T item) -> PrintHelper.printLine(" " + item));
    }

    /**
     * Prints a Map
     */
    public static <K, V> void printMap(String title, Map<K, V> map) {
        PrintHelper.printList(title,
                MapHelper.mapToList(map, (K key, V value) -> key + ": " + value));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private PrintHelper() {
        super();
    }
}
