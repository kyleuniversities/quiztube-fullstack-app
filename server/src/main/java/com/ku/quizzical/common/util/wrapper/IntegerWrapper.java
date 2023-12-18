package com.ku.quizzical.common.util.wrapper;

import com.ku.quizzical.common.helper.number.IntegerHelper;

public final class IntegerWrapper extends Wrapper<Integer> {
    // New Instance Methods
    public static IntegerWrapper newInstance() {
        return IntegerWrapper.newInstance(0);
    }

    public static IntegerWrapper newInstance(Integer value) {
        return new IntegerWrapper(value);
    }

    // Constructor Method
    private IntegerWrapper(Integer value) {
        super(value);
    }

    // Operant Return Methods
    public int getAbsoluteValue() {
        return IntegerHelper.absoluteValue(this.getValue());
    }

    public int getOppositeValue() {
        return -this.getValue();
    }

    public int getNextValue() {
        return this.getValue() + 1;
    }

    public int getPreviousValue() {
        return this.getValue() - 1;
    }

    public int getValueThenIncrement() {
        int value = this.getValue();
        this.increment();
        return value;
    }

    public int incrementThenGetValue() {
        this.increment();
        return this.getValue();
    }

    public int getValueThenDecrement() {
        int value = this.getValue();
        this.decrement();
        return value;
    }

    public int decrementThenGetValue() {
        this.decrement();
        return this.getValue();
    }

    // Operant Action Methods
    public void increment() {
        this.increment(1);
    }

    public void increment(int value) {
        this.setValue(this.getValue() + value);
    }

    public void decrement() {
        this.decrement(1);
    }

    public void decrement(int value) {
        this.increment(-value);
    }

    public void multiply(int value) {
        this.setValue(this.getValue() * value);
    }

    public void divide(int value) {
        this.setValue(this.getValue() / value);
    }

    public void mod(int value) {
        this.setValue(this.getValue() % value);
    }

    // Operant Relational Methods
    public boolean isEqualTo(int value) {
        return this.getValue() == value;
    }

    public boolean isLessThan(int value) {
        return this.getValue() < value;
    }

    public boolean isGreaterThan(int value) {
        return this.getValue() > value;
    }

    public boolean isLessThanOrEqualTo(int value) {
        return this.getValue() <= value;
    }

    public boolean isGreaterThanOrEqualTo(int value) {
        return this.getValue() >= value;
    }

    public boolean isNotEqualTo(int value) {
        return this.getValue() != value;
    }
}
