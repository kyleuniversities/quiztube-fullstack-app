package com.ku.quizzical.common.helper;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Helper class for Set Operations
 */
public final class SetHelper {
    /**
     * Adds elements to a set
     */
    public static <T> void addAll(Set<T> set, Iterable<T> iterable) {
        IterableHelper.forEach(iterable, (T item) -> SetHelper.addItem(set, item));
    }

    /**
     * Adds an element to a set
     */
    public static <T> void addItem(Set<T> set, T item) {
        set.add(item);
    }

    /**
     * Iterates through the elements of a set
     */
    public static <T> void forEach(Set<T> set, Consumer<T> action) {
        SetHelper.forEach(set, (Integer i, T item) -> action.accept(item));
    }

    /**
     * Iterates through the elements of a set and stops if a break point is reached
     */
    public static <T> boolean forEach(Set<T> set, Predicate<T> action) {
        return SetHelper.forEach(set, (Integer i, T item) -> action.test(item));
    }

    /**
     * Iterates through the elements of a set
     */
    public static <T> void forEach(Set<T> set, BiConsumer<Integer, T> action) {
        SetHelper.forEach(set, FunctionHelper.newBiPredicateFromBiConsumer(action));
    }

    /**
     * Iterates through the elements of a set and stops if a break point is reached
     */
    public static <T> boolean forEach(Set<T> set, BiPredicate<Integer, T> action) {
        int index = 0;
        for (T item : set) {
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
    private SetHelper() {
        super();
    }
}
