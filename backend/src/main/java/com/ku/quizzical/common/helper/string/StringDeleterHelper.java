package com.ku.quizzical.common.helper.string;

import com.ku.quizzical.common.helper.ConditionalHelper;

/**
 * Helper class for String Deletion Operations
 */
public final class StringDeleterHelper {
    /**
     * Deletes all instances of a specified character
     */
    public static String deleteAllInstances(String text, char target) {
        StringBuilder deleted = StringHelper.newBuilder();
        StringHelper.forEach(text,
                (Character ch) -> ConditionalHelper.ifThen(ch != target, () -> deleted.append(ch)));
        return deleted.toString();
    }

    /**
     * Deletes the last characters of a StringBuilder, the amount of characters specified by a given
     * length
     */
    public static void deleteLastCharacters(StringBuilder builder, int length) {
        builder.delete(builder.length() - length, builder.length());
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private StringDeleterHelper() {
        super();
    }
}
