package com.ku.quizzical.common.util.string;

import java.util.LinkedHashMap;
import java.util.Map;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility map for Strings
 */
public final class StringMap extends LinkedHashMap<String, String> {
    // New Instance Methods
    public static StringMap newInstance() {
        return new StringMap();
    }

    public static StringMap newInstance(String key, String value) {
        StringMap stringMap = StringMap.newInstance();
        stringMap.put(key, value);
        return stringMap;
    }

    public static StringMap newInstance(Map<String, String> map) {
        StringMap stringMap = StringMap.newInstance();
        MapHelper.forEach(map, (String key, String value) -> stringMap.put(key, value));
        return stringMap;
    }

    // Constructor
    private StringMap() {
        super();
    }

    // Accessor Methods
    public int getInteger(String key) {
        return Integer.parseInt(this.get(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(this.get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.get(key));
    }

    // Operant Methods
    public StringList toStringList() {
        StringList list = StringHelper.newStringList();
        MapHelper.forEach(this, (String key, String value) -> list.add(key + ": " + value));
        return list;
    }

    // To String Method
    @Override
    public String toString() {
        return this.toStringList().toString();
    }
}
