package com.ku.quizzical.common.util.entry;

/**
 * Utility key-value pair with values stored as instance fields
 */
public abstract class ValueEntry<K, V> implements Entry<K, V> {
    // Instance fields
    private K key;
    private V value;

    // Constructor Method
    protected ValueEntry(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }

    // Accessor Methods
    public final K getKey() {
        return this.key;
    }

    public final V getValue() {
        return this.value;
    }

    // Mutator Methods
    protected final void setKey(K key) {
        this.key = key;
    }

    protected final void setValue(V value) {
        this.value = value;
    }
}
