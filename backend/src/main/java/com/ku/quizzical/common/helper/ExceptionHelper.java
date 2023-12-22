package com.ku.quizzical.common.helper;

/**
 * Helper class for Exception Operations
 */
public final class ExceptionHelper {
    /**
     * Throws a new Illegal State Exception
     */
    public static void throwNewIllegalStateException(String message) {
        throw new IllegalStateException(message);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private ExceptionHelper() {
        super();
    }
}
