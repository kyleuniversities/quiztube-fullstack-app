package com.ku.quizzical.common.helper;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Helper class for Function Operations
 */
public final class FunctionHelper {
    /**
     * Creates a BiPredicate from a BiConsumer, returning a default true value
     */
    public static <T, U> BiPredicate<T, U> newBiPredicateFromBiConsumer(BiConsumer<T, U> action) {
        return FunctionHelper.newBiPredicateFromBiConsumer(action, true);
    }

    /**
     * Creates a BiPredicate from a BiConsumer, returning a default value
     */
    public static <T, U> BiPredicate<T, U> newBiPredicateFromBiConsumer(BiConsumer<T, U> action,
            boolean returnedValue) {
        return (T item1, U item2) -> FunctionHelper.performFallThroughAction(() -> action.accept(item1, item2),
                returnedValue);
    }

    /**
     * Creates a Consumer from a Runnable
     */
    public static <T> Consumer<T> newConsumerFromRunnable(Runnable runnable) {
        return (T item) -> runnable.run();
    }

    /**
     * Creates a Predicate from a consumer, returning a default true value
     */
    public static <T> Predicate<T> newPredicateFromConsumer(Consumer<T> action) {
        return FunctionHelper.newPredicateFromConsumer(action, true);
    }

    /**
     * Creates a Predicate from a consumer, returning a default value
     */
    public static <T> Predicate<T> newPredicateFromConsumer(Consumer<T> action, boolean returnedValue) {
        return (T item) -> FunctionHelper.performFallThroughAction(() -> action.accept(item), returnedValue);
    }

    /**
     * Performs an action and returns a value
     */
    public static <T> T performFallThroughAction(Runnable action, T returnedValue) {
        action.run();
        return returnedValue;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private FunctionHelper() {
        super();
    }
}
