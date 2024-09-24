package com.ku.quizzical.common.util.string;

import java.util.LinkedHashSet;
import com.ku.quizzical.common.helper.IterableHelper;
import com.ku.quizzical.common.helper.list.ListHelper;
import com.ku.quizzical.common.helper.string.StringCodeHelper;
import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility set for Strings
 */
public final class StringSet extends LinkedHashSet<String> {
    // New Instance Methods
    public static StringSet newInstance() {
        return new StringSet();
    }

    public static StringSet newInstance(Iterable<String> list) {
        StringSet stringSet = StringSet.newInstance();
        IterableHelper.forEach(list, (String string) -> {
            stringSet.add(string);
        });
        return stringSet;
    }

    public static StringSet newInstance(String[] array) {
        return StringSet.newInstance(ListHelper.toList(array));
    }

    // Constructor Method
    private StringSet() {
        super();
    }

    // Operant Methods
    public StringSet copy() {
        StringSet set = StringSet.newInstance();
        IterableHelper.forEach(this, (String string) -> set.add(string));
        return set;
    }

    // To String Method
    @Override
    public String toString() {
        return StringHelper.join(ListHelper.map(ListHelper.toList(this),
                (String text) -> StringCodeHelper.toCode(text)), "\n");
    }
}
