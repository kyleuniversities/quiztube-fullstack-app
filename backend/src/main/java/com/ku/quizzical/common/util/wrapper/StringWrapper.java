package com.ku.quizzical.common.util.wrapper;

public final class StringWrapper extends Wrapper<String> {
    // New Instance Methods
    public static StringWrapper newInstance(String value) {
        return new StringWrapper(value);
    }

    // Constructor Method
    private StringWrapper(String value) {
        super(value);
    }

    // Operant Relational Methods
    public boolean isEqualTo(String value) {
        return this.getValue().equals(value);
    }
}
