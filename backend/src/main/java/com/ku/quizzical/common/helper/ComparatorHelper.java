package com.ku.quizzical.common.helper;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Helper class for Comparison Operations
 */
public final class ComparatorHelper {
    /**
     * Returns a comparator of two items by applying a comparison of the aforementioned items mapped
     * to integers
     */
    public static <T> Comparator<T> newOrdinalComparator(Function<T, Integer> ordinalityCollector) {
        return (T item1, T item2) -> ordinalityCollector.apply(item1)
                - ordinalityCollector.apply(item2);
    }

    /**
     * Returns a comparator of two items by applying a reverse comparison of the aforementioned
     * items mapped to integers
     */
    public static <T> Comparator<T> newReversedOrdinalComparator(
            Function<T, Integer> ordinalityCollector) {
        return ComparatorHelper.reverse(ComparatorHelper.newOrdinalComparator(ordinalityCollector));
    }

    /**
     * Reverses a Compator
     */
    public static <T> Comparator<T> reverse(Comparator<T> comparator) {
        return comparator.reversed();
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ComparatorHelper() {
        super();
    }
}
