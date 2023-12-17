package com.ku.quizzical.common.util.entry;

/**
 * Utility key-value pair interface
 */
public interface Entry<K, V> {
    public K getKey();

    public V getValue();
}
