package com.ku.quizzical.common.helper;

import com.ku.quizzical.common.util.entry.Entry;
import com.ku.quizzical.common.util.entry.OrdinaryValueEntry;

/**
 * Helper class for Entry Operations
 */
public class EntryHelper {
    /**
     * Creates a new Entry
     */
    public static <K, V> Entry<K, V> newEntry(K key, V value) {
        return OrdinaryValueEntry.newInstance(key, value);
    }

    /**
     * Private Constructor to prevent instantiation
     */
    private EntryHelper() {
        super();
    }
}
