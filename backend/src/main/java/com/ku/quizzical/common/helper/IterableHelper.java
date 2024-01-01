package com.ku.quizzical.common.helper;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Helper class for Iterable Operations
 */
public final class IterableHelper {

    /**
     * Iterates through the elements of an iterable
     */
    public static <T> void forEach(Iterable<T> list, Consumer<T> action) {
        IterableHelper.forEach(list, (Integer i, T item) -> action.accept(item));
    }

    /**
     * Iterates through the elements of an iterable and stops if a break point is reached
     */
    public static <T> boolean forEach(Iterable<T> list, Predicate<T> action) {
        return IterableHelper.forEach(list, (Integer i, T item) -> action.test(item));
    }

    /**
     * Iterates through the elements of an iterable
     */
    public static <T> void forEach(Iterable<T> list, BiConsumer<Integer, T> action) {
        IterableHelper.forEach(list, FunctionHelper.newBiPredicateFromBiConsumer(action));
    }

    /**
     * Iterates through the elements of an iterable and stops if a break point is reached
     */
    public static <T> boolean forEach(Iterable<T> list, BiPredicate<Integer, T> action) {
        int index = 0;
        for (T item : list) {
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
    private IterableHelper() {
        super();
    }
}
