package com.ku.quizzical.common.util.wrapper;

public final class OrdinaryWrapper<T> extends Wrapper<T> {
    // New Instance Method
    public static <T> OrdinaryWrapper<T> newInstance(T value) {
        return new OrdinaryWrapper<>(value);
    }

    // Constructor Method
    private OrdinaryWrapper(T value) {
        super(value);
    }
}
