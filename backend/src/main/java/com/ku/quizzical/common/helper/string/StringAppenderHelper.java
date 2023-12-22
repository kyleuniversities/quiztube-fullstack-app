package com.ku.quizzical.common.helper.string;

import com.ku.quizzical.common.helper.IterationHelper;

/**
 * Helper class for String Appending Operations
 */
public final class StringAppenderHelper {
    /**
     * Appends a leading character a specified number of times
     */
    public static String appendLeadingCharacter(char character, int times, String text) {
        StringBuilder appended = StringHelper.newBuilder();
        IterationHelper.forEach(times, () -> appended.append(character));
        appended.append(text);
        return appended.toString();
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private StringAppenderHelper() {
        super();
    }
}
