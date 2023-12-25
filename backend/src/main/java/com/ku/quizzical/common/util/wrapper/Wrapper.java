package com.ku.quizzical.common.util.wrapper;

import com.ku.quizzical.common.helper.string.StringHelper;

/**
 * Utility class for
 */
public abstract class Wrapper<T> {
    // Instance Fields
    private T value;

    // Constructor Method
    protected Wrapper(T value) {
        super();
        this.value = value;
    }

    // Accessor Methods
    public final T getValue() {
        return this.value;
    }

    // Mutator Methods
    public final void setValue(T value) {
        this.value = value;
    }

    // Operant Methods
    public final T popValue() {
        return this.popValue(null);
    }

    public final T popValue(T newValue) {
        T popped = this.value;
        this.setValue(newValue);
        return popped;
    }

    // To String Method
    @Override
    public String toString() {
        return StringHelper.toString(this.value);
    }
}
