package com.ku.quizzical.common.util;

import java.time.Instant;
import com.ku.quizzical.common.helper.TimeHelper;
import com.ku.quizzical.common.helper.number.IndexHelper;

/**
 * Utility structure for representing time
 */
public final class Time {
    // Instance Fields
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millisecond;

    // New Instance Methods
    public static Time newInstance(Instant instant) {
        return Time.newInstance(TimeHelper.getInstantText(instant));
    }

    public static Time newInstance(String instantText) {
        return Time.newInstance(instantText.substring(0, 4), instantText.substring(5, 7),
                instantText.substring(8, 10), instantText.substring(11, 13),
                instantText.substring(14, 16), instantText.substring(17, 19),
                instantText.length() >= 23 ? instantText.substring(20, 23) : "000");
    }

    public static Time newInstance(String year, String month, String day, String hour,
            String minute, String second, String millisecond) {
        return Time.newInstance(Integer.parseInt(year), Integer.parseInt(month),
                Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute),
                Integer.parseInt(second), Integer.parseInt(millisecond));
    }

    public static Time newInstance(int year, int month, int day, int hour, int minute, int second,
            int millisecond) {
        return new Time(year, month, day, hour, minute, second, millisecond);
    }

    // Constructor Method
    private Time(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }

    // Accessor Methods
    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public int getMillisecond() {
        return this.millisecond;
    }

    // To String Method
    @Override
    public String toString() {
        return String.format("(%s-%s-%s--%s-%s-%s-%s)", IndexHelper.toIndexText(this.year, 4),
                IndexHelper.toIndexText(this.month, 2), IndexHelper.toIndexText(this.day, 2),
                IndexHelper.toIndexText(this.hour, 2), IndexHelper.toIndexText(this.minute, 2),
                IndexHelper.toIndexText(this.second, 2),
                IndexHelper.toIndexText(this.millisecond, 3));
    }
}
