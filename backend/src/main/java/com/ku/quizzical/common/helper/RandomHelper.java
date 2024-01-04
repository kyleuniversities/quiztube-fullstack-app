package com.ku.quizzical.common.helper;

import java.util.Random;

/**
 * Helper class for Random Operations
 */
public final class RandomHelper {
    /**
     * Class Fields
     */
    private static final Random GENERATOR = RandomHelper.newRandom();

    /**
     * Generates a random number
     */
    public static int nextInt(int bound) {
        return RandomHelper.GENERATOR.nextInt(bound);
    }

    /**
     * Generates a random number within a range
     */
    public static int nextIntWithinRange(int start, int upTo) {
        int length = upTo - start;
        return start + RandomHelper.nextInt(length);
    }

    /**
     * Makes a new Random Object
     */
    public static Random newRandom() {
        return new Random();
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private RandomHelper() {
        super();
    }
}
