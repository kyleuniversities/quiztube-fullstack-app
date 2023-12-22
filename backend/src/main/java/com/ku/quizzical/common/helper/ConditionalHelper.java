package com.ku.quizzical.common.helper;

import java.util.function.Supplier;

/**
 * Helper class for Conditional Operations
 */
public final class ConditionalHelper {
    /**
     * Returns one value if a condition is true, or another if it is false
     */
    public static <T> T ifReturnElse(boolean condition, T acceptanceValue, T rejectionValue) {
        return condition ? acceptanceValue : rejectionValue;
    }

    /**
     * Performs an action if a condition is met
     */
    public static <T> void ifThen(boolean condition, Runnable action) {
        if (condition) {
            action.run();
        }
    }

    /**
     * Returns one value if a condition is true, or another if it is false
     */
    public static <T> T newTernaryOperation(boolean condition, Supplier<T> acceptanceSupplier,
            Supplier<T> rejectionSupplier) {
        return condition ? acceptanceSupplier.get() : rejectionSupplier.get();
    }

    /**
     * Performs an action for as long as a condition is true
     */
    public static void whileLoop(Supplier<Boolean> condition, Runnable action) {
        while (condition.get()) {
            action.run();;
        }
    }

    /**
     * Performs an action and repeats it for as long as condition is true
     */
    public static void whileLoopUntilFalse(Supplier<Boolean> action) {
        boolean isIterating = true;
        while (isIterating) {
            isIterating = action.get();
        }
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ConditionalHelper() {
        super();
    }
}
