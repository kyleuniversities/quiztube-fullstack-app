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

    // Accessor Methods
    public int length() {
        return this.getValue().length();
    }

    public boolean isEmpty() {
        return this.getValue().isEmpty();
    }

    // Operant Relational Methods
    public boolean isEqualTo(String value) {
        return this.getValue().equals(value);
    }
}
