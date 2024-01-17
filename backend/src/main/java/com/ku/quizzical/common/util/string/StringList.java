package com.ku.quizzical.common.util.string;

import java.util.ArrayList;
import java.util.List;
import com.ku.quizzical.common.helper.list.ListHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility list for Strings
 */
public final class StringList extends ArrayList<String> {
    // New Instance Methods
    public static StringList newInstance() {
        return new StringList();
    }

    public static StringList newInstance(List<String> list) {
        StringList stringList = StringList.newInstance();
        ListHelper.forEach(list, (String string) -> stringList.add(string));
        return stringList;
    }

    public static StringList newInstance(String[] array) {
        return StringList.newInstance(ListHelper.toList(array));
    }

    // Constructor Method
    private StringList() {
        super();
    }

    // Accessor Methods
    public int getInteger(int index) {
        return Integer.parseInt(this.get(index));
    }

    public double getDouble(int index) {
        return Double.parseDouble(this.get(index));
    }

    public boolean getBoolean(int index) {
        return Boolean.parseBoolean(this.get(index));
    }

    // Operant Methods
    public StringList copy() {
        StringList copy = StringHelper.newStringList();
        ListHelper.forEach(this, (String text) -> copy.add(text));
        return copy;
    }

    // To String Method
    @Override
    public String toString() {
        return StringHelper.join(this, "\n");
    }
}
