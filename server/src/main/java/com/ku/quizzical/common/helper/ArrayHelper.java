package com.ku.quizzical.common.helper;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Helper class for Array Operations
 */
public final class ArrayHelper {
    /**
     * Iterates through the elements of an Array
     */
    public static <T> void forEach(T[] array, Consumer<T> action) {
        ArrayHelper.forEach(array, (Integer i, T item) -> action.accept(item));
    }

    /**
     * Iterates through the elements of a list and stops if a break point is reached
     */
    public static <T> boolean forEach(T[] array, Predicate<T> action) {
        return ArrayHelper.forEach(array, (Integer i, T item) -> action.test(item));
    }

    /**
     * Iterates through the elements of a list
     */
    public static <T> void forEach(T[] array, BiConsumer<Integer, T> action) {
        ArrayHelper.forEach(array, FunctionHelper.newBiPredicateFromBiConsumer(action));
    }

    /**
     * Iterates through the elements of an array and stops if a break point is
     * reached
     */
    public static <T> boolean forEach(T[] array, BiPredicate<Integer, T> action) {
        int index = 0;
        for (T item : array) {
            if (!action.test(index, item)) {
                return false;
            }
            index++;
        }
        return true;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ArrayHelper() {
        super();
    }
}
