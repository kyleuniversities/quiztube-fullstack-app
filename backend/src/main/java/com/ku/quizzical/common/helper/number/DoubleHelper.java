package com.ku.quizzical.common.helper.number;

import com.ku.quizzical.common.helper.ConditionalHelper;

/**
 * Helper class for String Operations
 */
public final class DoubleHelper {
    /**
     * Returns the absolute value of an doubleeger
     */
    public static double absoluteValue(double value) {
        return ConditionalHelper.ifReturnElse(value < 0, -value, value);
    }

    /**
     * Operation for greater than
     */
    public static boolean greaterThan(double a, double b) {
        return a > b;
    }

    /**
     * Operation for less than
     */
    public static boolean lessThan(double a, double b) {
        return a < b;
    }

    /**
     * Converts a double to decimal text
     */
    public static String toDecimalText(double value, int numberOfDecimalDigits) {
        return String.format("%." + numberOfDecimalDigits + "f", value);
    }

    /**
     * Converts a double to decimal text in hundredths
     */
    public static String toDecimalTextInHundredths(double value) {
        return DoubleHelper.toDecimalText(value, 2);
    }

    /**
     * Converts a double to decimal text in millionths
     */
    public static String toDecimalTextInMillionths(double value) {
        return DoubleHelper.toDecimalText(value, 6);
    }

    /**
     * Converts a double to decimal text in tenths
     */
    public static String toDecimalTextInTenths(double value) {
        return DoubleHelper.toDecimalText(value, 1);
    }

    /**
     * Converts a double to decimal text in thousandths
     */
    public static String toDecimalTextInThousandths(double value) {
        return DoubleHelper.toDecimalText(value, 3);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private DoubleHelper() {
        super();
    }
}
