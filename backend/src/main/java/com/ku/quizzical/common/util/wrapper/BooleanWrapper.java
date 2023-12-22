package com.ku.quizzical.common.util.wrapper;

public final class BooleanWrapper extends Wrapper<Boolean> {
    // New Instance Methods
    public static BooleanWrapper newTrueInstance() {
        return BooleanWrapper.newInstance(true);
    }

    public static BooleanWrapper newFalseInstance() {
        return BooleanWrapper.newInstance(false);
    }

    public static BooleanWrapper newInstance(Boolean value) {
        return new BooleanWrapper(value);
    }

    // Constructor Method
    private BooleanWrapper(Boolean value) {
        super(value);
    }

    // Operant Return Methods
    public boolean isTrue() {
        return this.getValue();
    }

    public boolean isFalse() {
        return !this.isTrue();
    }

    // Mutator Methods
    public void setValueToTrue() {
        this.setValue(true);
    }

    public void setValueToFalse() {
        this.setValue(false);
    }
}
