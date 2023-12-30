package com.ku.quizzical.common.util.string;

import java.util.LinkedHashMap;
import java.util.Map;
import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ListHelper;
import com.ku.quizzical.common.helper.MapHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility map for String Lists
 */
public final class StringListMap extends LinkedHashMap<String, StringList> {
    // New Instance Methods
    public static StringListMap newInstance() {
        return new StringListMap();
    }

    public static StringListMap newInstance(Map<String, StringList> map) {
        StringListMap stringMap = StringListMap.newInstance();
        MapHelper.forEach(map, (String key, StringList value) -> stringMap.put(key, value));
        return stringMap;
    }

    // Constructor
    private StringListMap() {
        super();
    }

    // Accessor Methods
    public String get(String key1, int index) {
        return this.get(key1).get(index);
    }

    public int getInteger(String key1, int index) {
        return this.get(key1).getInteger(index);
    }

    public double getDouble(String key1, int index) {
        return this.get(key1).getDouble(index);
    }

    public boolean getBoolean(String key1, int index) {
        return this.get(key1).getBoolean(index);
    }

    // Mutator Methods
    public StringList getInitalize(String key1) {
        return MapHelper.getInitialize(this, key1, () -> StringList.newInstance());
    }

    // Operant Methods
    public StringList toStringList() {
        StringList list = StringHelper.newStringList();
        MapHelper.forEach(this, (String key1, StringList itemList) -> {
            ListHelper.add(list, "<<" + key1 + ">>");
            ListHelper.addAll(list, itemList);
            ListHelper.add(list, "");
            ListHelper.add(list, StringHelper.repeatText("-", 30));
        });
        ConditionalHelper.ifThen(!this.isEmpty(), () -> ListHelper.removeLastItems(list, 2));
        return list;
    }

    public StringListMap copy() {
        StringListMap listMap = StringListMap.newInstance();
        MapHelper.forEach(this, (String key, StringList list) -> listMap.put(key, list.copy()));
        return listMap;
    }

    // To String Method
    @Override
    public String toString() {
        return this.toStringList().toString();
    }
}
