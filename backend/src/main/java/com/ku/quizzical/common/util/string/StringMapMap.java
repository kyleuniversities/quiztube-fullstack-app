package com.ku.quizzical.common.util.string;

import java.util.LinkedHashMap;
import java.util.Map;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility map for String Maps
 */
public final class StringMapMap extends LinkedHashMap<String, StringMap> {
    // New Instance Methods
    public static StringMapMap newInstance() {
        return new StringMapMap();
    }

    public static StringMapMap newInstance(Map<String, StringMap> map) {
        StringMapMap stringMap = StringMapMap.newInstance();
        MapHelper.forEach(map, (String key, StringMap value) -> stringMap.put(key, value));
        return stringMap;
    }

    // Constructor
    private StringMapMap() {
        super();
    }

    // Accessor Methods
    public String get(String key1, String key2) {
        return this.get(key1).get(key2);
    }

    public int getInteger(String key1, String key2) {
        return this.get(key1).getInteger(key2);
    }

    public double getDouble(String key1, String key2) {
        return this.get(key1).getDouble(key2);
    }

    public boolean getBoolean(String key1, String key2) {
        return this.get(key1).getBoolean(key2);
    }

    // Mutator Methods
    public StringMap getInitalize(String key1) {
        return MapHelper.getInitialize(this, key1, () -> StringMap.newInstance());
    }

    // Operant Methods
    public StringList toStringList() {
        StringList list = StringHelper.newStringList();
        MapHelper.forEach(this, (String key1, StringMap map) -> {
            ListHelper.add(list, "<<" + key1 + ">>");
            ListHelper.addAll(list, map.toStringList());
            ListHelper.add(list, "");
            ListHelper.add(list, StringHelper.repeatText("-", 30));
        });
        ConditionalHelper.ifThen(!this.isEmpty(), () -> ListHelper.removeLastItems(list, 2));
        return list;
    }

    public StringMapMap copy() {
        StringMapMap mapMap = StringMapMap.newInstance();
        MapHelper.forEach(this, (String key, StringMap map) -> mapMap.put(key, map.copy()));
        return mapMap;
    }

    // To String Method
    @Override
    public String toString() {
        return this.toStringList().toString();
    }
}
