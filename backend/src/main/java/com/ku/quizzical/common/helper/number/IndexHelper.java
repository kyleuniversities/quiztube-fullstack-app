package com.ku.quizzical.common.helper.number;

import com.ku.quizzical.common.helper.string.StringAppenderHelper;

public class IndexHelper {
    /**
     * Appends leading zeroes a specified number of times
     */
    public static String appendLeadingZeroes(int value, int numberOfLeadingZeroes) {
        return StringAppenderHelper.appendLeadingCharacter('0', numberOfLeadingZeroes, value + "");
    }

    /**
     * Converts an integer to an index text with a specified number of digits
     */
    public static String toIndexText(int value, int numberOfDigits) {
        return IndexHelper.appendLeadingZeroes(value, numberOfDigits - IntegerHelper.length(value));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private IndexHelper() {
        super();
    }
}
