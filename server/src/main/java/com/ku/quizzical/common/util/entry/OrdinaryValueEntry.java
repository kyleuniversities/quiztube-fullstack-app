package com.ku.quizzical.common.util.entry;

/**
 * Utility key-value pair with immutable values stored as instance fields
 */
public final class OrdinaryValueEntry<K, V> extends ValueEntry<K, V> {
    // New Instance Method
    public static <K, V> OrdinaryValueEntry<K, V> newInstance(K key, V value) {
        return new OrdinaryValueEntry<>(key, value);
    }

    // Constructor Method
    private OrdinaryValueEntry(K key, V value) {
        super(key, value);
    }
}
