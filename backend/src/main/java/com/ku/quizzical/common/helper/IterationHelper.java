package com.ku.quizzical.common.helper;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import com.ku.quizzical.common.helper.number.IntegerHelper;

/**
 * Helper class for Iterative Operations
 */
public final class IterationHelper {
    /**
     * Performs an action a given number of times
     */
    public static void forEach(int upTo, Runnable action) {
        IterationHelper.forEach(upTo, FunctionHelper.newConsumerFromRunnable(action));
    }

    /**
     * Loops through a range of given values
     */
    public static void forEach(int upTo, Consumer<Integer> action) {
        IterationHelper.forEach(upTo, FunctionHelper.newPredicateFromConsumer(action));
    }

    /**
     * Loops through a range of given values and stops if a break point is reached
     */
    public static boolean forEach(int upTo, Predicate<Integer> action) {
        return IterationHelper.forEach(0, upTo, action);
    }

    /**
     * Loops through a range of given values
     */
    public static void forEach(int start, int upTo, Consumer<Integer> action) {
        IterationHelper.forEach(start, upTo, FunctionHelper.newPredicateFromConsumer(action));
    }

    /**
     * Loops through a range of given values and stops if a break point is reached
     */
    public static boolean forEach(int start, int upTo, Predicate<Integer> action) {
        int increment = ConditionalHelper.ifReturnElse(start < upTo, 1, -1);
        return IterationHelper.forEach(start, upTo, increment, action);
    }

    /**
     * Loops through a range of given values and stops if a break point is reached
     */
    public static boolean forEach(int start, int upTo, int increment, Predicate<Integer> action) {
        BiPredicate<Integer, Integer> actionCondition = ConditionalHelper
                .ifReturnElse(increment > 0, IntegerHelper::lessThan, IntegerHelper::greaterThan);
        return IterationHelper.forEach(start, upTo, increment, actionCondition, action);
    }

    /**
     * Loops through a range of given values and stops if a break point is reached
     */
    public static boolean forEach(int start, int upTo, int increment,
            BiPredicate<Integer, Integer> actionCondition, Predicate<Integer> action) {
        for (int i = start; actionCondition.test(i, upTo); i += increment) {
            if (!action.test(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private IterationHelper() {
        super();
    }
}
