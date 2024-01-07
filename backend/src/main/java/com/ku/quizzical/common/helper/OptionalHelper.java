package com.ku.quizzical.common.helper;

import java.util.Optional;

/**
 * Helper class for Optional Operations
 */
public class OptionalHelper {
    /**
     * Gets the value of an Optional or returns null if the Optional is not present
     */
    public static <T> T getApparentValue(Optional<T> optional) {
        return ConditionalHelper.newTernaryOperation(optional.isPresent(), () -> optional.get(),
                () -> null);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private OptionalHelper() {
        super();
    }
}
