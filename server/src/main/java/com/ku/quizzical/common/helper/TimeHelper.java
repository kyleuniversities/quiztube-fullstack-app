package com.ku.quizzical.common.helper;

import java.time.Instant;
import com.ku.quizzical.common.util.Time;

/**
 * Helper class for Time Operations
 */
public final class TimeHelper {
    /**
     * Constant for shifting to Pacific Standard Time. Subtract by 7 hours.
     */
    public static final long PST_SHIFT = -7 * 60 * 60 * 1000;

    /**
     * Gets the current time instant
     */
    public static Instant getInstant() {
        return TimeHelper
                .getInstantFromEpochMilliseconds(TimeHelper.getInstantEpochMillisecondsInPst());
    }

    /**
     * Gets the current time instant epoch milliseconds - Number of milliseconds from (0:00:00 GMT
     * January 1, 1970) + a shift so that the time displayed will be in PST
     */
    public static long getInstantEpochMillisecondsInPst() {
        return Instant.now().toEpochMilli() + TimeHelper.PST_SHIFT;
    }

    /**
     * Gets the current instant from the number of milliseconds elapsed from (0:00:00 GMT January 1,
     * 1970)
     */
    public static Instant getInstantFromEpochMilliseconds(long epochMilliseconds) {
        return Instant.ofEpochMilli(epochMilliseconds);
    }

    /**
     * Gets the String representation of the current Instance object
     */
    public static String getInstantText() {
        return TimeHelper.getInstantText(TimeHelper.getInstant());
    }

    /**
     * Gets the String representation of a specified Instance object
     */
    public static String getInstantText(Instant instant) {
        return instant.toString();
    }

    /**
     * Gets the current time
     */
    public static Time getTime() {
        return Time.newInstance(TimeHelper.getInstant());
    }

    /**
     * Gets the current time from an instant
     */
    public static Time getTime(Instant instant) {
        return Time.newInstance(instant);
    }

    /**
     * Gets a String representing the current time usable as a file name and sortable by
     * alphanumeric comparison
     */
    public static String getTimeText() {
        return TimeHelper.getTime(TimeHelper.getInstant()).toString();
    }

    /**
     * Gets a String representing the time from an instant usable as a file name and sortable by
     * alphanumeric comparison
     */
    public static String getTimeText(Instant instant) {
        return TimeHelper.getTime(instant).toString();
    }

    /**
     * Gets a String representing the time from a specific epoch milliseconds usable as a file name
     * and sortable by alphanumeric comparison
     */
    public static String getTimeText(long epochMilliseconds) {
        return TimeHelper
                .getTimeText(TimeHelper.getInstantFromEpochMilliseconds(epochMilliseconds));
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private TimeHelper() {
        super();
    }
}
