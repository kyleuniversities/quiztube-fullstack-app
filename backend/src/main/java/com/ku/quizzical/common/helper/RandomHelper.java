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
