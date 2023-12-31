package com.ku.quizzical.common.helper;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Helper class for Comparison Operations
 */
public final class ComparatorHelper {
    /**
     * Returns a comparator of two items by applying a comparison of the aforementioned items mapped
     * to comparables
     */
    public static <T, U extends Comparable<U>> Comparator<T> newMappedComparator(
            Function<T, U> mapper) {
        return (T item1, T item2) -> mapper.apply(item1).compareTo(mapper.apply(item2));
    }

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
     * Returns a comparator of two items by applying a comparison of the aforementioned items mapped
     * to booleans with true being first
     */
    public static <T> Comparator<T> newConditionPresentFirstComparator(
            Predicate<T> conditionDetector) {
        return (T item1, T item2) -> ComparatorHelper
                .trueFirstCompare(conditionDetector.test(item1), conditionDetector.test(item2));
    }

    /**
     * Returns a comparator of two items by applying a comparison of the aforementioned items mapped
     * to booleans with true being last
     */
    public static <T> Comparator<T> newConditionPresentLastComparator(
            Predicate<T> conditionDetector) {
        return ComparatorHelper
                .reverse(ComparatorHelper.newConditionPresentFirstComparator(conditionDetector));
    }

    /**
     * Reverses a Compator
     */
    public static <T> Comparator<T> reverse(Comparator<T> comparator) {
        return comparator.reversed();
    }

    /**
     * Compares booleans by choosing the true value to be first
     */
    private static int trueFirstCompare(boolean boolean1, boolean boolean2) {
        if (boolean1 && boolean2) {
            return 0;
        }
        if (boolean1 && !boolean2) {
            return 1;
        }
        if (!boolean1 && boolean2) {
            return -1;
        }
        // if (!boolean1 && !boolean2)
        return 0;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ComparatorHelper() {
        super();
    }
}
