package com.ku.quizzical.common.helper.string;

import com.ku.quizzical.common.helper.ConditionalHelper;

/**
 * Helper class for String Trim Operations
 */
public final class StringTrimmerHelper {
    /**
     * Trims leading white space
     */
    public static String trimLeadingWhiteSpace(String text) {
        int nonWhiteSpaceIndex =
                StringHelper.indexOf(text, StringTrimmerHelper::isNonWhiteSpaceCharacter);
        return ConditionalHelper.newTernaryOperation(nonWhiteSpaceIndex > -1,
                () -> StringHelper.substring(text, nonWhiteSpaceIndex), () -> text);
    }

    /**
     * Trims surrounding white space
     */
    public static String trimSurroundingWhiteSpace(String text) {
        int startNonWhiteSpaceIndex =
                StringHelper.indexOf(text, StringTrimmerHelper::isNonWhiteSpaceCharacter);
        if (startNonWhiteSpaceIndex == -1) {
            return text;
        }
        int lastNonWhiteSpaceIndex =
                StringHelper.lastIndexOf(text, StringTrimmerHelper::isNonWhiteSpaceCharacter);
        return StringHelper.substring(text, startNonWhiteSpaceIndex, lastNonWhiteSpaceIndex + 1);
    }

    /**
     * Trims trailing white space
     */
    public static String trimTrailingWhiteSpace(String text) {
        int nonWhiteSpaceIndex =
                StringHelper.lastIndexOf(text, StringTrimmerHelper::isNonWhiteSpaceCharacter);
        return ConditionalHelper.newTernaryOperation(nonWhiteSpaceIndex > -1,
                () -> StringHelper.substring(text, 0, nonWhiteSpaceIndex + 1), () -> text);
    }

    /**
     * Helper method for depicting a non white space character
     */
    private static boolean isNonWhiteSpaceCharacter(Character ch) {
        return ch != ' ' && ch != '\t' && ch != '\n';
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private StringTrimmerHelper() {
        super();
    }
}
